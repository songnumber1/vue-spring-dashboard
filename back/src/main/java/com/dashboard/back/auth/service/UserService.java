package com.dashboard.back.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dashboard.back.auth.entity.setting.User;
import com.dashboard.back.auth.model.JwtModel;
import com.dashboard.back.auth.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void setLoginProc(User user, JwtModel jwtModel) {
		user.setToken(jwtModel.getRefreshToken());
		user.setTokenExpired(jwtModel.getRefreshTokenExpirationDate());

		userRepository.save(user);
	}

	@Transactional
	public void setRefreshTokenEmpty(User user) {
		if (user == null)
			return;

		user.setToken(null);
		user.setTokenExpired(null);

		userRepository.save(user);
	}
}
