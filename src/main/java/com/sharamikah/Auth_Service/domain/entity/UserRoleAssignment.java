package com.sharamikah.Auth_Service.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "userroleassignment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
)
public class UserRoleAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "role_id", nullable = false)
    private String roleId;

    @Column(name = "assigned_by")
    private String assignedBy;

    @Column(name = "assigned_at", nullable = false, updatable = false)
    private Instant assignedAt;

    @Column(name = "role_version")
    private String roleVersion;

    @Column(name = "active")
    private boolean active = false;

    @Column(name = "tenant_id")
    private Long tenantId;

    public UserRoleAssignment() {
        this.assignedAt = Instant.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }

    public String getRoleId() { return roleId; }
    public void setRoleId(String roleId) { this.roleId = roleId; }

    public String getAssignedBy() { return assignedBy; }
    public void setAssignedBy(String assignedBy) { this.assignedBy = assignedBy; }

    public Instant getAssignedAt() { return assignedAt; }

    public String getRoleVersion() { return roleVersion; }
    public void setRoleVersion(String roleVersion) { this.roleVersion = roleVersion; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
}
