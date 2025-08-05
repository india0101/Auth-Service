package com.sharamikah.Auth_Service.DTO;


import lombok.Data;

/**
 * Payload for updating mutable user fields.
 */
@Data
public class UpdateAppUserRequest {

    private String firstName;
    private String lastName;
    /**
     * Toggle for soft‐delete or deactivation.
     */
    private Boolean active;
}

