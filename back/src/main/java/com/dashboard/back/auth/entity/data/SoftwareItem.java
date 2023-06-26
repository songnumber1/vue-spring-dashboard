package com.dashboard.back.auth.entity.data;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class SoftwareItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Lob
    private String content;

    @CreationTimestamp
    private LocalDateTime writeDate;

    private String remark;

    private long deptMenu;

    private long menuId;
}
