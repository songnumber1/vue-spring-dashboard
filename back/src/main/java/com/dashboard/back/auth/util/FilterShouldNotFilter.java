package com.dashboard.back.auth.util;

import java.util.Collection;
import java.util.LinkedHashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;

public class FilterShouldNotFilter {
	public static boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        Collection<String> excludeUrlPatterns = new LinkedHashSet<>();
        excludeUrlPatterns.add("/css/**");
        excludeUrlPatterns.add("/assets/**");
        excludeUrlPatterns.add("/js/**");
        excludeUrlPatterns.add("/auth/joinForm");
        
        return excludeUrlPatterns.stream()
                                 .anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getServletPath()));
	}
}
