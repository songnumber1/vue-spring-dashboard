package com.dashboard.back.auth.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class AuthLogoutHandler extends SecurityContextLogoutHandler {

	//private String[] cookies;
	private AuthWorkHandler authLogoutWorkHandler;
	
	public AuthLogoutHandler(AuthWorkHandler authLogoutWorkHandler) {
		this.authLogoutWorkHandler = authLogoutWorkHandler;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		authLogoutWorkHandler.logoutDataDelete(request, response);

		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(null);

		SecurityContextHolder.clearContext();
	}
}
