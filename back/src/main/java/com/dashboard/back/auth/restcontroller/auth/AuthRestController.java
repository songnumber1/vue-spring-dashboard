package com.dashboard.back.auth.restcontroller.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.back.auth.exception.AccountExceptionType;
import com.dashboard.back.auth.repository.UserRepository;
import com.dashboard.back.auth.util.ResponseData;
import com.dashboard.back.auth.entity.setting.User;
import com.dashboard.back.auth.exception.AccountException;

@RestController
@RequestMapping("/auth/api")
public class AuthRestController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/joinProc")
	public ResponseEntity<?> save(@RequestBody User user) {
		if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null
				|| user.getPassword().isEmpty()) {
			throw new AccountException(AccountExceptionType.REUQIRED_PARAMETER_ERROR);
		}

		User userEntity = userRepository.findByUsername(user.getUsername());

		if (userEntity != null) {
			throw new AccountException(AccountExceptionType.DUPLICATED_USER);
		}

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);

		return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", null, null);
	}

	@GetMapping("/test")
	public ResponseEntity<?> test() {
		Map<String, String> result = new HashMap<String, String>();

		result.put("data", "1234");

		return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", result, null);
	}
}
