package com.sharamikah.Auth_Service.domain.entity;

import com.sharamikah.Auth_Service.domain.enums.AccountStatus;
import com.sharamikah.Auth_Service.domain.enums.AuthProvider;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.UUID;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    private AuthProvider authProvider = AuthProvider.EMAIL;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @Column(name = "is_verified", nullable = false)
    private boolean verified = false;

    @Column(name = "mfa_enabled", nullable = false)
    private boolean mfaEnabled = false;

    @Column(name = "session_risk_score", nullable = false)
    private Integer sessionRiskScore = 0;

    private Instant termsAcceptedAt;
    private Instant privacyPolicyAcceptedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauthscopegrant", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "scope")
    private Set<String> oauthScopes = new HashSet<>();

    private Instant lastLogin;
    private Long tenantId;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private String activeRoleId;
    private Instant roleSyncedAt;

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DeviceSession> deviceSessions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LoginHistory> loginHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ConsentLog> consents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SecurityEventLog> securityEvents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRoleAssignment> roleAssignments = new ArrayList<>();

    // --- Getters and Setters ---
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isMfaEnabled() {
        return mfaEnabled;
    }

    public void setMfaEnabled(boolean mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
    }

    public Integer getSessionRiskScore() {
        return sessionRiskScore;
    }

    public void setSessionRiskScore(Integer sessionRiskScore) {
        this.sessionRiskScore = sessionRiskScore;
    }

    public Instant getTermsAcceptedAt() {
        return termsAcceptedAt;
    }

    public void setTermsAcceptedAt(Instant termsAcceptedAt) {
        this.termsAcceptedAt = termsAcceptedAt;
    }

    public Instant getPrivacyPolicyAcceptedAt() {
        return privacyPolicyAcceptedAt;
    }

    public void setPrivacyPolicyAcceptedAt(Instant privacyPolicyAcceptedAt) {
        this.privacyPolicyAcceptedAt = privacyPolicyAcceptedAt;
    }

    public Set<String> getOauthScopes() {
        return oauthScopes;
    }

    public void setOauthScopes(Set<String> oauthScopes) {
        this.oauthScopes = oauthScopes;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getActiveRoleId() {
        return activeRoleId;
    }

    public void setActiveRoleId(String activeRoleId) {
        this.activeRoleId = activeRoleId;
    }

    public Instant getRoleSyncedAt() {
        return roleSyncedAt;
    }

    public void setRoleSyncedAt(Instant roleSyncedAt) {
        this.roleSyncedAt = roleSyncedAt;
    }

    public List<DeviceSession> getDeviceSessions() {
        return deviceSessions;
    }

    public void setDeviceSessions(List<DeviceSession> deviceSessions) {
        this.deviceSessions = deviceSessions;
    }

    public List<LoginHistory> getLoginHistories() {
        return loginHistories;
    }

    public void setLoginHistories(List<LoginHistory> loginHistories) {
        this.loginHistories = loginHistories;
    }

    public List<ConsentLog> getConsents() {
        return consents;
    }

    public void setConsents(List<ConsentLog> consents) {
        this.consents = consents;
    }

    public List<SecurityEventLog> getSecurityEvents() {
        return securityEvents;
    }

    public void setSecurityEvents(List<SecurityEventLog> securityEvents) {
        this.securityEvents = securityEvents;
    }

    public List<UserRoleAssignment> getRoleAssignments() {
        return roleAssignments;
    }

    public void setRoleAssignments(List<UserRoleAssignment> roleAssignments) {
        this.roleAssignments = roleAssignments;
    }

    // --- Utility Methods ---
    public boolean hasScope(String scope) {
        return oauthScopes.contains(scope);
    }

    public void addScope(String scope) {
        oauthScopes.add(scope);
    }

    public void removeScope(String scope) {
        oauthScopes.remove(scope);
    }

    public boolean isActive() {
        return AccountStatus.ACTIVE.equals(this.accountStatus);
    }

    public void recordLogin(Instant time) {
        this.lastLogin = time;
        this.sessionRiskScore = 0; // reset upon verified login
    }

    public void verifyTermsAndPolicy() {
        Instant now = Instant.now();
        if (termsAcceptedAt == null) termsAcceptedAt = now;
        if (privacyPolicyAcceptedAt == null) privacyPolicyAcceptedAt = now;
    }

    public boolean hasRole(String roleId) {
        return roleAssignments.stream().anyMatch(r -> r.getRoleId().equals(roleId));
    }
}
