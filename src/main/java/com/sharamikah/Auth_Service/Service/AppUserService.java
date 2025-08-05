package com.sharamikah.Auth_Service.Service;

import com.sharamikah.Auth_Service.DTO.AppUserDTO;
import com.sharamikah.Auth_Service.DTO.CreateAppUserRequest;
import com.sharamikah.Auth_Service.DTO.UpdateAppUserRequest;

import java.util.Optional;

public interface AppUserService {

    /** Register a new user account */
    AppUserDTO createUser(CreateAppUserRequest request);

    /** Fetch user by internal ID */
    Optional<AppUserDTO> getUserById(Long userId);

    /** Fetch user by email */
    Optional<AppUserDTO> getUserByEmail(String email);

    /** Update mutable profile fields */
    AppUserDTO updateUser(Long userId, UpdateAppUserRequest request);

    /** Delete (or deactivate) a user */
    void deleteUser(Long userId);

    /** Check existence by email */
    boolean emailExists(String email);
}

