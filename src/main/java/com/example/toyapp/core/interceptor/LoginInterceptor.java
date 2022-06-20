package com.example.toyapp.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI = request.getRequestURI();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("accessToken") == null
				|| session.getAttribute("loginUser") == null) {
			log.info("[IN] LoginInterceptor 미인증 사용자 요청");
			log.info("requestUrl ========= {}",requestURI);
			
			// 로그인으로 redirect
			response.sendRedirect("/toyapp/test/login.do");
			return false;
		}else {
			log.info("[IN] LoginInterceptor 인증 사용자 요청");
			log.info("requestUrl ========= {}",requestURI);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("==================== postHandle END ======================");
	}
}
