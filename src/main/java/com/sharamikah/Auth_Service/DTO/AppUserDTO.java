package com.sharamikah.Auth_Service.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * The data we expose to clients when returning user info.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
