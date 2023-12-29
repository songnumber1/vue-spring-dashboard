package com.dashboard.back.auth.entity.setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @Column(name = "userRoleid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userRoleid;

    private long roleno;

    private String securityrole;

    private String role;

    private String remark;
}