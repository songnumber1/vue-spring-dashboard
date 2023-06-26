package com.dashboard.back.auth.security.handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dashboard.back.auth.common.AuthConst;
import com.dashboard.back.auth.entity.setting.User;
import com.dashboard.back.auth.repository.UserRepository;
import com.dashboard.back.auth.service.UserService;
import com.dashboard.back.auth.token.JwtTokenProvider;

@Component
public class AuthWorkHandler {
	@Autowired
	private AuthConst authConst;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public void logoutDataDelete(HttpServletRequest request, HttpServletResponse response, User user) {
		Cookie[] cookies = request.getCookies(); // 모든 쿠키의 정보를 cookies에 저장

		if (cookies != null) {
			Stream<Cookie> cookieStream = Arrays.stream(cookies);

			var tokenCookien = cookieStream.filter((item) -> {
				return item.getName().equals(authConst.getCOOKIE_HEADER_NAME());
			}).findFirst();

			if (tokenCookien.isPresent()) {
				tokenCookien.get().setMaxAge(0); // 유효시간을 0으로 설정
				tokenCookien.get().setPath("/"); // 이거 안하면 안된다.
				response.addCookie(tokenCookien.get()); // 응답 헤더에 추가
				response.addHeader("Set-Cookie", null);
			}
		}

		if (user != null){
			userService.setRefreshTokenEmpty(user);
		}

		SecurityContextHolder.clearContext();

		try {
			response.sendRedirect(authConst.getCONTEXT_PATH());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logoutDataDelete(HttpServletRequest request, HttpServletResponse response) {
		User user = null;

		Cookie[] cookies = request.getCookies(); // 모든 쿠키의 정보를 cookies에 저장
		if (cookies != null) {
			Stream<Cookie> cookieStream = Arrays.stream(cookies);

			var tokenCookien = cookieStream.filter((item) -> {
				return item.getName().equals(authConst.getCOOKIE_HEADER_NAME());
			}).findFirst();

			try {
				if (tokenCookien.isPresent()) {
					String accessToken = tokenCookien.get().getValue();

					if (StringUtils.isNotBlank(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
						String userName = jwtTokenProvider.getClaims(accessToken, "sub");

						if (userName != null) {
							try {
								user = userRepository.findByUsername(userName);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					// access 토큰 만료시 refresh 토큰 가져오기
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			tokenCookien.get().setMaxAge(0); // 유효시간을 0으로 설정
			tokenCookien.get().setPath("/"); // 이거 안하면 안된다.
			response.addCookie(tokenCookien.get()); // 응답 헤더에 추가
			response.addHeader("Set-Cookie", null);
		}

		if (user != null){
			userService.setRefreshTokenEmpty(user);
		}

		SecurityContextHolder.clearContext();

		try {
			response.sendRedirect(authConst.getCOOKIE_HEADER_NAME());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean beforeIsLoginValication(HttpServletRequest request, HttpServletResponse response) {
		if (request.getRequestURI().equals(authConst.getLOGIN_FULL_URL())) {
			try {
				response.sendRedirect(authConst.getCONTEXT_PATH());
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		return false;
	}
}