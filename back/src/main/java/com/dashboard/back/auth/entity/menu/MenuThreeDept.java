package com.dashboard.back.auth.entity.menu;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;
import com.dashboard.back.auth.entity.setting.UserRole;

@Data
@Entity
public class MenuThreeDept {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long menuThreeDeptid;

	private String menuThreeDeptTitle;

	private String menuThreeDeptUrl;

	private boolean active;

	private String menuThreeDeptIcon;

	@ColumnDefault("1")
	private int menuThreeDeptSort;

	private String remark;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menuTwoDeptid")
	private MenuTwoDept menuTwoDept;

	@OneToOne
	// @JsonIgnore
	@JoinColumn(name = "userRoleid", nullable = false)
	private UserRole userRole;
}
