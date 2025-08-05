package com.sharamikah.Auth_Service.DTO;


import com.sharamikah.Auth_Service.domain.enums.SecurityEventType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.Instant;
import java.util.Map;

/**
 * Represents a security-related event in the system.
 */
@Data
public class SecurityEventLogDTO {

    /** Unique identifier for this event */
    private Long id;

    /** User who triggered or is the subject of the event */
    @NotNull(message = "User ID is required")
    private Long userId;

    /** Type of security event (LOGIN_SUCCESS, TOKEN_REVOKED, etc.) */
    @NotNull(message = "Event type is required")
    private SecurityEventType eventType;

    /** When the event occurred */
    @NotNull(message = "Event timestamp is required")
    private Instant eventTimestamp;

    /** Any additional event details or context */
    private Map<String, Object> metadata;
}

