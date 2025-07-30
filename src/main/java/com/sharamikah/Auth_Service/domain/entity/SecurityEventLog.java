package com.sharamikah.Auth_Service.domain.entity;

import com.sharamikah.Auth_Service.domain.enums.SecurityEventType;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "securityeventlog")
public class SecurityEventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private SecurityEventType eventType;

    @Column(name = "event_timestamp", nullable = false, updatable = false)
    private Instant eventTimestamp;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    public SecurityEventLog() {
        this.eventTimestamp = Instant.now(); // safeguard for default timestamp
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public SecurityEventType getEventType() {
        return eventType;
    }

    public void setEventType(SecurityEventType eventType) {
        this.eventType = eventType;
    }

    public Instant getEventTimestamp() {
        return eventTimestamp;
    }

    // Only setter if update is allowed; otherwise keep read-only

    //Use immutable logs: Consider avoiding setters for fields like eventTimestamp
    // if it must reflect the creation moment.

    //public void setEventTimestamp(Instant eventTimestamp) {
    //    this.eventTimestamp = eventTimestamp;
    //}

    public String getMetadata() {
        return metadata;
    }

    //Audit trail: Persist log origin context (e.g., client IP, session ID)
    // inside metadata for richer postmortem analysis.

    //  public void setMetadata(String metadata) {
   //     this.metadata = metadata;
   // }
}
