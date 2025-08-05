package com.sharamikah.Auth_Service.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "permissions")
@Data
public class Permission {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}