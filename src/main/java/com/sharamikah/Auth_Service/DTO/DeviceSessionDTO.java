package com.sharamikah.Auth_Service.DTO;

import lombok.Data;

import java.time.Instant;

/**
 * Data returned when querying or creating a device session.
 */
@Data
public class DeviceSessionDTO {

    private Long sessionId;
    private Long userId;
    private String deviceName;
    private String ipAddress;
    private String userAgent;

    /**
     * When the session was first created.
     */
    private Instant createdAt;

    /**
     * Last activity timestamp (e.g. last token refresh).
     */
    private Instant lastAccessedAt;

    /**
     * Scheduled expiration (e.g. refresh token TTL).
     */
    private Instant expiresAt;
}

