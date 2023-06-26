package com.dashboard.back.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dashboard.back.auth.entity.setting.User;;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	User findByUsernameAndPassword(String username, String password);

	@Query(value = "SELECT new com.dashboard.back.auth.entity.setting.User(u.id, u.username, u.roles, u.active, u.email) FROM User u where u.roles = :role")
	List<User> findAccountList(@Param("role") String role);
}
