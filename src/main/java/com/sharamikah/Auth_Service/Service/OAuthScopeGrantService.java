package com.sharamikah.Auth_Service.Service;


import com.sharamikah.Auth_Service.DTO.OAuthScopeGrantDTO;
import java.util.List;

public interface OAuthScopeGrantService {

    /** Grant one or more scopes to a user */
    List<OAuthScopeGrantDTO> grantScopes(Long userId, List<String> scopeIds);

    /** Revoke a previously granted scope */
    void revokeScope(Long userId, String scopeId);

    /** List currently granted scopes */
    List<OAuthScopeGrantDTO> getGrantedScopes(Long userId);
}

