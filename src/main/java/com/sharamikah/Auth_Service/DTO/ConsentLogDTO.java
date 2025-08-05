package com.sharamikah.Auth_Service.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a record of a user’s consent action.
 */
@Data
public class ConsentLogDTO {

    /** Unique identifier for this consent record */
    private Long id;

    /** User who gave or revoked consent */
    @NotNull(message = "User ID is required")
    private Long userId;

    /** The consent category or purpose (e.g. "email_marketing") */
    @NotBlank(message = "Consent type is required")
    @Size(max = 100, message = "Consent type must be 100 characters or fewer")
    private String consentType;

    /** Whether the user granted (true) or revoked (false) consent */
    @NotNull(message = "Consent flag is required")
    private Boolean granted;

    /** When the consent action occurred */
    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

    /** Source IP address (v4 or v6) */
    @Pattern(
            regexp = "^(?:\\d{1,3}\\.){3}\\d{1,3}$|^([0-9a-fA-F]{0,4}:){1,7}[0-9a-fA-F]{0,4}$",
            message = "Must be a valid IPv4 or IPv6 address"
    )
    private String ipAddress;

    /** Optional user-agent string for device context */
    @Size(max = 512, message = "User agent string too long")
    private String userAgent;

    /** Any additional metadata (e.g. version, locale) */
    private Map<String, String> metadata;
}

