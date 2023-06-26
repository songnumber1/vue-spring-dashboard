package com.dashboard.back.auth.entity.data;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class SiteItem {
    public enum SiteType {
        bg_primary,
        bg_secondary,
        bg_success,
        bg_danger,
        bg_warning,
        bg_info,
        bg_light,
        bg_dark
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String siteName;

    private String siteId;

    private String sitePw;

    private String siteUrl;

    private String siteThumbnail;

    @Enumerated(EnumType.STRING)
    private SiteType siteType = SiteType.bg_primary;

    @CreationTimestamp
    private LocalDateTime writeDate;

    private String remark;
}
