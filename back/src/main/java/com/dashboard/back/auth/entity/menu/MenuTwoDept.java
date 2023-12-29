package com.dashboard.back.auth.entity.menu;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;
import com.dashboard.back.auth.entity.setting.UserRole;

@Data
@Entity
public class MenuTwoDept {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long menuTwoDeptid;

	private String menuTwoDeptTitle;

	private String menuTwoDeptUrl;

	private boolean active;

	private String menuTwoDeptIcon;

	@ColumnDefault("1")
	private int menuTwoDeptSort;

	private String remark;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menuOneDeptid")
	private MenuOneDept menuOneDept;

	@JsonManagedReference
	@OneToMany(mappedBy = "menuTwoDept", fetch = FetchType.LAZY)
	private List<MenuThreeDept> menuThreeDepts = new ArrayList<>();

	@OneToOne
	// @JsonIgnore
	@JoinColumn(name = "userRoleid", nullable = false)
	private UserRole userRole;
}
