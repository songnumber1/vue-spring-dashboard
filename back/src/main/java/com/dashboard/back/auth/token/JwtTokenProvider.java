package com.dashboard.back.auth.token;

import io.jsonwebtoken.*;
import com.dashboard.back.auth.entity.setting.User;
import com.dashboard.back.auth.model.JwtModel;
import com.dashboard.back.auth.repository.UserRepository;
import com.dashboard.back.auth.security.Principal.PrincipalDetails;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class JwtTokenProvider {
	/* (1) */
	@Value("${jwt.secret-key}")
	private String SECRET_KEY;

	@Value("${jwt.header-name}")
	private String HEADER_NAME;

	@Value("${jwt.access-token-expire-length}")
	private long ACCESS_VALIDITY_IN_MILLISECONDS;

	@Value("${jwt.refresh-token-expire-length}")
	private long REFRESH_VALIDITY_IN_MILLISECONDS;

	@Autowired
	private UserRepository userRepository;

	/* (2) */
	@PostConstruct
	protected void init() {
		SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
	}

	/**
	 * 토큰 생성
	 * 
	 * @param username
	 * @return
	 */
	public JwtModel createToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", username);

		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date accessDate = new Date(now.getTime() + ACCESS_VALIDITY_IN_MILLISECONDS);
		Date refreshDate = new Date(now.getTime() + REFRESH_VALIDITY_IN_MILLISECONDS);

		return JwtModel.builder().accessToken(this.generateToken(claims, now, accessDate))
				.refreshToken(this.generateToken(claims, now, refreshDate))
				.accessTokenExpirationDate(sdf.format(accessDate)).refreshTokenExpirationDate(sdf.format(refreshDate))
				.build();
	}

	/**
	 * 토큰 발급
	 * 
	 * @param claims
	 * @param now
	 * @param expirationDate
	 * @return
	 */
	private String generateToken(Map<String, Object> claims, Date now, Date expirationDate) {
		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
	}

	/**
	 * 토큰 검증
	 * 
	 * @param token
	 * @return
	 */
	public boolean validateToken(String token) {
		try {
			this.extractAllClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw e;
		}
	}

	/**
	 * 토큰을 헤더에 저장
	 * 
	 * @param response
	 * @param token
	 */
	public void saveToken(HttpServletResponse response, String token) {
		response.setHeader(HEADER_NAME, token);
	}

	/**
	 * 헤더에 있는 토큰 분석
	 * 
	 * @param request
	 * @return
	 */
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(HEADER_NAME);
		if (StringUtils.isNotBlank(bearerToken)) {
			return bearerToken;
		}
		return null;
	}

	/**
	 * 토큰을 쿠키에 저장
	 * 
	 * @param response
	 * @param token
	 */
	public void createCookie(HttpServletResponse response, String token) {
		ResponseCookie cookie = ResponseCookie.from(HEADER_NAME, token).httpOnly(true).sameSite("lax")
				.maxAge(ACCESS_VALIDITY_IN_MILLISECONDS).path("/").build();
		response.addHeader("Set-Cookie", cookie.toString());
	}

	/**
	 * 쿠키에 있는 토큰을 분석
	 * 
	 * @param request
	 * @return
	 */
	public String resolveCookie(HttpServletRequest request) {
		final Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(HEADER_NAME)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * 스프링 시큐리티 검증
	 * 
	 * @param token
	 * @return
	 */
	public Authentication getAuthentication(String token) {
		User userEntity = userRepository.findByUsername(this.getClaims(token, "sub"));

		PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
		Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
				principalDetails.getAuthorities());

		return authentication;
	}

	/**
	 * 토큰 Claims 분석
	 * 
	 * @param token
	 * @return
	 * @throws ExpiredJwtException
	 */
	public Jws<Claims> extractAllClaims(String token) throws ExpiredJwtException {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
	}

	/**
	 * 토큰에서 Claims 추출
	 * 
	 * @param token
	 * @return
	 */
	public String getClaims(String token, String key) {
		return this.extractAllClaims(token).getBody().get(key, String.class);
	}
}
