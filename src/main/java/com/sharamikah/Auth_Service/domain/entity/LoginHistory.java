package com.sharamikah.Auth_Service.domain.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Records every user login with metadata for security auditing and analytics.
 */
@Entity
@Table(
        name = "login_history",
        indexes = {
                @Index(name = "idx_login_history_user", columnList = "user_id"),
                @Index(name = "idx_login_history_timestamp", columnList = "login_timestamp")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who logged in.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_login_history_user"))
    private AppUser user;

    /**
     * Timestamp of the login event; set automatically on insert.
     */
    @CreatedDate
    @CreationTimestamp
    @Column(name = "login_timestamp", nullable = false, updatable = false)
    private Instant loginTimestamp;

    /**
     * IP address from which the login occurred.
     */
    @NotBlank
    @Size(max = 45)  // supports IPv6
    @Column(name = "ip_address", nullable = false, length = 45)
    private String ipAddress;

    /**
     * Geolocation or named location (city, region).
     */
    @Size(max = 100)
    @Column(name = "location", length = 100)
    private String location;

    /**
     * Client device name or fingerprint.
     */
    @Size(max = 100)
    @Column(name = "device_name", length = 100)
    private String deviceName;

    /**
     * Full User-Agent string for the login request.
     */
    @Size(max = 512)
    @Column(name = "user_agent", length = 512)
    private String userAgent;

    /**
     * Risk score computed for this session (e.g., 0–100).
     */
    @Min(0)
    @Max(100)
    @Column(name = "session_risk_score")
    private Integer sessionRiskScore;

    /**
     * Role identifier the user assumed upon login.
     */
    @Size(max = 36)
    @Column(name = "active_role_id", length = 36)
    private String activeRoleId;

    /**
     * Version of role template applied to this session.
     */
    @Size(max = 20)
    @Column(name = "role_template_version", length = 20)
    private String roleTemplateVersion;

    //--------------------------------------------------------------------------------------------------
    // equals() and hashCode() — only based on ID for identity consistency
    //--------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginHistory)) return false;
        return id != null && id.equals(((LoginHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    //--------------------------------------------------------------------------------------------------
    // toString() — avoid loading lazy relations
    //--------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "LoginHistory{" +
                "id=" + id +
                ", loginTimestamp=" + loginTimestamp +
                ", ipAddress='" + ipAddress + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", sessionRiskScore=" + sessionRiskScore +
                ", activeRoleId='" + activeRoleId + '\'' +
                '}';
    }
}
