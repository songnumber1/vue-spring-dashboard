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
public class MenuOneDept {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long menuOneDeptid;

	private String menuOneDeptTitle;

	private String menuOneDeptUrl;

	private boolean active;

	private String menuOneDeptIcon;

	@ColumnDefault("1")
	private int menuOneDeptSort;

	private String remark;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	private MenuCategory menuCategory;

	@JsonManagedReference
	@OneToMany(mappedBy = "menuOneDept", fetch = FetchType.LAZY)
	private List<MenuTwoDept> menuTwoDepts = new ArrayList<MenuTwoDept>();

	@OneToOne
	// @JsonIgnore
	@JoinColumn(name = "userRoleid", nullable = false)
	private UserRole userRole;
}
