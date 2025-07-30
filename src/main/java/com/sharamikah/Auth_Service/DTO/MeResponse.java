package com.sharamikah.Auth_Service.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeResponse {

    private Long id;
    private String email;
    private String authProvider;
    private String accountStatus;
    private boolean isVerified;
    private Set<String> oauthScopes;
    private String activeRoleId;
    private Long tenantId;
    private Instant createdAt;
}
