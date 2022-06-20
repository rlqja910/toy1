package com.example.toyapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.toyapp.core.util.HttpRequestHelper;
import com.example.toyapp.dao.KakaoLoginUserInfoJsonReq;
import com.example.toyapp.dao.KakaoUserInfoReq;
import com.example.toyapp.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = { "${base-path}/login" })
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	//////////////사용안함///////////////////
	@RequestMapping(value = { "/login.json" })
	public @ResponseBody Map<String, Object> dashboardMain(HttpServletRequest request, Model model, @RequestBody KakaoLoginUserInfoJsonReq kakaoLoginUserInfoJsonReq) {

		System.out.println("==dashboardMain=="); 
		System.out.println(kakaoLoginUserInfoJsonReq);
		
		//ip 추출
		String userIp = null;
	    
		userIp = request.getHeader("X-Forwarded-For");
	    
	    if (userIp == null || userIp.length() == 0 || "unknown".equalsIgnoreCase(userIp)) {
	    	userIp = request.getHeader("Proxy-Client-IP");
	    }
	    if (userIp == null || userIp.length() == 0 || "unknown".equalsIgnoreCase(userIp)) {
	    	userIp = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (userIp == null || userIp.length() == 0 || "unknown".equalsIgnoreCase(userIp)) {
	    	userIp = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (userIp == null || userIp.length() == 0 || "unknown".equalsIgnoreCase(userIp)) {
	    	userIp = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (userIp == null || userIp.length() == 0 || "unknown".equalsIgnoreCase(userIp)) {
	    	userIp = request.getRemoteAddr();
	    }
	    
	    if(userIp == null) {
	    	userIp="";
	    }
		
		System.out.println(userIp);
		//
		
		KakaoUserInfoReq kakaoUserInfoReq = new KakaoUserInfoReq();
		
		kakaoUserInfoReq.setUserId(kakaoLoginUserInfoJsonReq.getId());
		kakaoUserInfoReq.setUserNm(kakaoLoginUserInfoJsonReq.getName());
		kakaoUserInfoReq.setEmailAdr(kakaoLoginUserInfoJsonReq.getEmail());
		kakaoUserInfoReq.setProfile(kakaoLoginUserInfoJsonReq.getProfile());
		
		int result = loginService.updateKakaoUser(kakaoUserInfoReq, userIp);
		
		System.out.println(result);
		
		//로그인 세션 생성
		HttpSession session = request.getSession();
		if(result == 1) {
			System.out.println("로그인 세션 생성됨");
			session.setAttribute("loginUser", kakaoLoginUserInfoJsonReq);
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();

		// 0 : fail, 1 : success
		returnMap.put("success", result);

		HttpRequestHelper.successWrapperJson(returnMap, model);

		return returnMap;
		
	}
}
