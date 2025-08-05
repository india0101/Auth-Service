package com.sharamikah.Auth_Service.Service.Impl;


import com.sharamikah.Auth_Service.DTO.AppUserDTO;
import com.sharamikah.Auth_Service.Exception.EmailAlreadyInUseException;
import com.sharamikah.Auth_Service.Exception.UserNotFoundException;
import com.sharamikah.Auth_Service.Mapper.AppUserMapper;
import com.sharamikah.Auth_Service.Repository.UserRepository;
import com.sharamikah.Auth_Service.Service.AppUserService;
import com.sharamikah.Auth_Service.domain.entity.AppUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;
    private final AppUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(UserRepository userRepository,
                              AppUserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public AppUserDTO createUser(AppUserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyInUseException(dto.getEmail());
        }
        AppUser entity = userMapper.toEntity(dto);
        // Hash the raw password before saving
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        AppUser saved = userRepository.save(entity);
        return userMapper.toDto(saved);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public AppUserDTO getUserById(Long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public AppUserDTO updateUser(Long id, AppUserDTO dto) {
        AppUser existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!existing.getEmail().equals(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyInUseException(dto.getEmail());
        }

        userMapper.updateEntityFromDto(dto, existing);
        AppUser updated = userRepository.save(existing);
        return userMapper.toDto(updated);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<AppUserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}