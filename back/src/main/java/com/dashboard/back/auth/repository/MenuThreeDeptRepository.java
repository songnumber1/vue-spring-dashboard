package com.dashboard.back.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.back.auth.entity.menu.MenuThreeDept;

@Repository
public interface MenuThreeDeptRepository extends JpaRepository<MenuThreeDept, Long> {

}
