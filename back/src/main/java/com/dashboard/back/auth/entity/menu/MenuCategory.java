package com.dashboard.back.auth.entity.menu;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;
import com.dashboard.back.auth.entity.setting.UserRole;

@Data
@Entity
public class MenuCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;

	private String categoryTitle;

	private boolean active;

	private String categoryIcon;

	@ColumnDefault("1")
	private int categorySort;

	private String remark;

	@JsonManagedReference
	@OneToMany(mappedBy = "menuCategory", fetch = FetchType.LAZY)
	private List<MenuOneDept> menuOneDepts = new ArrayList<>();

	@OneToOne
	// @JsonIgnore
	@JoinColumn(name = "userRoleid", nullable = false)
	private UserRole userRole;
}
