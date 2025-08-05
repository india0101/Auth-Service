package com.sharamikah.Auth_Service.DTO;

import lombok.Data;

import java.time.Instant;

/**
 * Represents a granted OAuth scope for a user.
 */
@Data
public class OAuthScopeGrantDTO {

    /** Unique identifier for this grant */
    private Long grantId;

    /** User to whom the scope is granted */
    private Long userId;

    /** Identifier of the granted scope */
    private String scopeId;

    /** When the scope was granted */
    private Instant grantedAt;

    /** Optional expiration for the grant (if applicable) */
    private Instant expiresAt;
}

