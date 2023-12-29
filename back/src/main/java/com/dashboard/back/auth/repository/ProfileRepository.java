package com.dashboard.back.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.back.auth.entity.profile.Profile;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
