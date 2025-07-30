package com.sharamikah.Auth_Service.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "oauth_scope_grants")
public class OAuthScopeGrant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @NotBlank
    @Column(nullable = false)
    private String scope;

    @Column(name = "granted_at", nullable = false, updatable = false)
    private Instant grantedAt;

    @PrePersist
    protected void onCreate() {
        this.grantedAt = Instant.now();
    }

    // Constructors
    public OAuthScopeGrant() {}

    public OAuthScopeGrant(AppUser user, String scope) {
        this.user = user;
        this.scope = scope;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Instant getGrantedAt() {
        return grantedAt;
    }
}
