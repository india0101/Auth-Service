package com.sharamikah.Auth_Service.Controller;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
public class JwkSetController {

    private final KeyPair keyPair;

    public JwkSetController(KeyPair keyPair) { this.keyPair = keyPair; }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        RSAKey jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("authservice-key")
                .build();
        return new JWKSet(jwk).toJSONObject();
    }
}

