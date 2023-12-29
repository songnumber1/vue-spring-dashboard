package com.dashboard.back.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.ExpiredJwtException;
import com.dashboard.back.auth.entity.setting.User;
import com.dashboard.back.auth.repository.UserRepository;
import com.dashboard.back.auth.security.handler.AuthWorkHandler;
import com.dashboard.back.auth.token.JwtTokenProvider;
import com.dashboard.back.auth.util.FilterShouldNotFilter;

// 시큐리티가 Filter를 가지고 있는데 그 중 BasicAuthenticationFilter 라는것이 있음
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어 있음.
// 만약에 권한이 인증이 필요한 주소가 아니라면 이 필터는 호출되지 않음
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository;
	private JwtTokenProvider jwtTokenProvider;
	private AuthWorkHandler authWorkHandler;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository,
			JwtTokenProvider jwtTokenProvider, AuthWorkHandler authLogoutWorkHandler) {
		super(authenticationManager);
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.authWorkHandler = authLogoutWorkHandler;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String accessToken = jwtTokenProvider.resolveCookie(request);
		String refreshToken = null;

		// access 토큰 검증 과정
		try {
			if (StringUtils.isNotBlank(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
				// 로그인 상태에서 loginForm으로 이동 시 메인 페이지로 이동
				if (authWorkHandler.beforeIsLoginValication(request, response)) {
					return;
				} else {
					Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
			// access 토큰 만료시 refresh 토큰 가져오기
		} catch (ExpiredJwtException e) {
			User userModel = userRepository.findByUsername(e.getClaims().getSubject());

			if (ObjectUtils.isNotEmpty(userModel)) {
				refreshToken = userModel.getToken();
			}
			// Access Token이 비정상일 경우
		} catch (Exception e) {
			// 로그아웃을 해야한다. (logoutDataDelete)
			// SecurityContextHolder.clearContext();

			authWorkHandler.logoutDataDelete(request, response, null);
			return;
		}

		// refresh 토큰으로 access 토큰 재 발급
		if (StringUtils.isNotBlank(refreshToken)) {
			try {
				try {
					if (jwtTokenProvider.validateToken(refreshToken)) {
						Authentication auth = jwtTokenProvider.getAuthentication(refreshToken);
						SecurityContextHolder.getContext().setAuthentication(auth);

						// 새로운 access 토큰 발급
						String newAccessToken = jwtTokenProvider
								.createToken(jwtTokenProvider.getClaims(refreshToken, "sub")).getAccessToken();
						jwtTokenProvider.createCookie(response, newAccessToken);

						if (authWorkHandler.beforeIsLoginValication(request, response)) {
							return;
						}
					}
				} catch (ExpiredJwtException e) {
					// Logout을 해야한다.
					// SecurityContextHolder.clearContext();

					authWorkHandler.logoutDataDelete(request, response, null);
					return;
				}
			} catch (Exception e) {
				// SecurityContextHolder.clearContext();
				e.printStackTrace();
				authWorkHandler.logoutDataDelete(request, response, null);
				return;
			}
		}

		// if(accessToken == null) {
		// 	response.sendRedirect(StaticVariable.CONTEXT_PATH);
		// 	return;
		// }

		chain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return FilterShouldNotFilter.shouldNotFilter(request);
	}
}
