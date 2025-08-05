package com.sharamikah.Auth_Service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

/**
 * Data Transfer Object for assigning roles to users.
 */
@Data
public class UserRoleAssignmentDTO {

    /** Unique identifier for this assignment record */
    private Long id;

    /** ID of the user to whom the role is assigned */
    @NotNull(message = "User ID is required")
    private Long userId;

    /** Identifier of the role being assigned */
    @NotNull(message = "Role ID is required")
    private String roleId;

    /** Timestamp when the role was assigned */
    private Instant assignedAt;

    /** Principal or system component that performed the assignment */
    private String assignedBy;
}

