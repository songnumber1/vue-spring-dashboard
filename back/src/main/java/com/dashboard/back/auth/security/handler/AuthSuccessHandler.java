package com.dashboard.back.auth.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;
import com.dashboard.back.auth.model.JwtModel;
import com.dashboard.back.auth.security.Principal.PrincipalDetails;
import com.dashboard.back.auth.service.UserService;
import com.dashboard.back.auth.token.JwtTokenProvider;

@RequiredArgsConstructor
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		System.out.println("successfulAuthentication 실행됨 : 인증이완료되었습니다.");
		
		PrincipalDetails principalDetailis = (PrincipalDetails)authentication.getPrincipal();
		
		System.out.println(principalDetailis.getUser());
		
		JwtModel jwtModel = jwtTokenProvider.createToken(principalDetailis.getUser().getUsername());
		
		userService.setLoginProc(principalDetailis.getUser(), jwtModel);
		
        if (StringUtils.isNotBlank(jwtModel.getAccessToken())) {
            jwtTokenProvider.createCookie(response, jwtModel.getAccessToken());
        }
        
		super.onAuthenticationSuccess(request, response, authentication);
	}
}