package com.dashboard.back.auth.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class LoggerAdvice {
	private static final Logger log = LoggerFactory.getLogger(LoggerAdvice.class);

	@Around("execution(* com.dashboard.back.auth.controller..*Controller.*(..))")
	public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable {
	    try {

	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

	        String controllerName = joinPoint.getSignature().getDeclaringType().getName();
	        String methodName = joinPoint.getSignature().getName();
	        Map<String, Object> params = new HashMap<String, Object>();

	        try {
	            params.put("controller", controllerName);
	            params.put("method", methodName);
	            params.put("params", getParams(request));
	            params.put("log_time", LocalDateTime.now());
	            params.put("request_uri", request.getRequestURI());
	            params.put("http_method", request.getMethod());
	        } catch (Exception e) {
	            log.error("LoggerAspect error", e);
	        }

	        log.info("로그 내용 : {}", params);

	        Object result = joinPoint.proceed();

	        return result;
	    } catch (Throwable throwable) {
	        throw throwable;
	    }
	}


	private JSONObject getParams(HttpServletRequest request) throws JSONException {
	    JSONObject jsonObject = new JSONObject();
	    Enumeration<String> params = request.getParameterNames();
	    while (params.hasMoreElements()) {
	        String param = params.nextElement();
	        String replaceParam = param.replaceAll("\\.", "-");
	        jsonObject.put(replaceParam, request.getParameter(param));
	    }
	    return jsonObject;
	}
}