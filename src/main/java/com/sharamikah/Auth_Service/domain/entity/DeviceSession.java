package com.sharamikah.Auth_Service.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "device_session")
public class DeviceSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "refresh_token_hash", nullable = false, length = 128)
    private String refreshTokenHash;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // Constructors
    protected DeviceSession() {
        // For JPA
    }

    public DeviceSession(AppUser user, String ipAddress, String deviceName, String userAgent,
                         String refreshTokenHash, Instant expiresAt) {
        this.user = user;
        this.ipAddress = ipAddress;
        this.deviceName = deviceName;
        this.userAgent = userAgent;
        this.refreshTokenHash = refreshTokenHash;
        this.expiresAt = expiresAt;
        this.createdAt = Instant.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public String getRefreshTokenHash() { return refreshTokenHash; }
    public void setRefreshTokenHash(String refreshTokenHash) { this.refreshTokenHash = refreshTokenHash; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public Instant getCreatedAt() { return createdAt; }

    // Equals & hashCode for entity identity
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceSession that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString for logging (optional)
    @Override
    public String toString() {
        return "DeviceSession{id=" + id + ", user=" + user.getId() +
                ", deviceName='" + deviceName + '\'' + ", ip='" + ipAddress + '\'' + '}';
    }
}
