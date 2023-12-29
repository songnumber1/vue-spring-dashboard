package com.dashboard.back.auth.security.Principal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dashboard.back.auth.entity.setting.User;
import com.dashboard.back.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;

// http://localhost:8089/login
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService의 loadUserByUsername입니다.");

		User userEntity = userRepository.findByUsername(username);

		if (userEntity == null)
			return null;
		else {
			if (userEntity.isActive())
				return new PrincipalDetails(userEntity);
			else
				return null;
		}
	}

}
