package com.sharamikah.Auth_Service.Mapper;



import com.sharamikah.Auth_Service.DTO.AppUserDTO;
import com.sharamikah.Auth_Service.domain.entity.AppUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUserDTO toDto(AppUser entity);

    AppUser toEntity(AppUserDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AppUserDTO dto, @MappingTarget AppUser entity);
}

