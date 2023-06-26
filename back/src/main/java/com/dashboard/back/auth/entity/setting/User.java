package com.dashboard.back.auth.entity.setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String username;

	private String password;

	private String roles;

	@JsonIgnore
	private String token;

	@JsonIgnore
	private String tokenExpired;

	private boolean active;

	private String email;

	private String remark;

	public List<String> GetRoleList() {
		if (this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}

		return new ArrayList<>();
	}

	public User(long id, String username, String roles, boolean active, String email) {
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.active = active;
		this.email = email;
	}
}