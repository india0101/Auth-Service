//package com.sharamikah.Auth_Service.DTO;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//
//public class UserDto {
//    @NotNull(message = "{NotNull.user.username}")
//    private String username;
//
//    @NotBlank(message = "{NotBlank.user.password}")
//    @Size(min = 8, max = 32, message = "{Size.user.password}")
//    private String password;
//
//    @Email(message = "{Email}")
//    private String email;
//    // getters/setters…
//}
//
