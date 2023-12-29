package com.dashboard.back.auth.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class AuthConst {
	@Value("${server.servlet.context-path}")
	public String CONTEXT_PATH;

	@Value("${application.login.suffix-url}")
	private String LOGIN_SUFFIX_URL;

    @Value("${application.login.full-url}")
	private String LOGIN_FULL_URL;

	@Value("${application.login.proc-url}")
	private String LOGIN_PROC_URL;

	@Value("${application.logout.url}")
	private String LOGOUT_FULL_URL;

	@Value("${application.logout.proc-url}")
	private String LOGOUT_PROC_URL;

	// Access 토큰 쿠키 이름
	@Value("${jwt.header-name}")
	public String COOKIE_HEADER_NAME;
}
