package com.sharamikah.Auth_Service.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

/**
 * Represents a single login event for a user.
 */
@Data
public class LoginHistoryDTO {

    /** Unique identifier for this login event */
    private Long id;

    /** User performing the login */
    @NotNull(message = "User ID is required")
    private Long userId;

    /** Timestamp of when the attempt occurred */
    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

    /** Source IP address (v4 or v6) */
    @NotBlank(message = "IP address is required")
    @Pattern(
            regexp = "^(?:\\d{1,3}\\.){3}\\d{1,3}$|^([0-9a-fA-F]{0,4}:){1,7}[0-9a-fA-F]{0,4}$",
            message = "Must be a valid IPv4 or IPv6 address"
    )
    private String ipAddress;

    /** Browser or device user-agent string */
    @NotBlank(message = "User agent is required")
    @Size(max = 512, message = "User agent string too long")
    private String userAgent;

    /** Whether the attempt succeeded */
    @NotNull(message = "Success flag is required")
    private Boolean successful;

    /** Optional reason for failure (if unsuccessful) */
    @Size(max = 1000, message = "Failure reason too long")
    private String failureReason;
}

