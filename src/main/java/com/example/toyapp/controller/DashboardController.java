package com.example.toyapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.toyapp.dao.KakaoUserInfoReq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = { "${base-path}/dashboard" })
public class DashboardController {

	@RequestMapping(value = { "/main" })
	public String dashboard(HttpServletRequest request, Model model) {

		log.info("[IN]DashboardController dashboard");
		
		System.out.println((String)request.getSession().getAttribute("accessToken"));
		System.out.println((KakaoUserInfoReq)request.getSession().getAttribute("loginUser"));
		model.addAttribute("loginUser", request.getSession().getAttribute("loginUser"));
		
		if((String)request.getSession().getAttribute("accessToken") == null || (KakaoUserInfoReq)request.getSession().getAttribute("loginUser") == null ) {
			
			log.info("[OUT]DashboardController dashboard");
			
			return "redirect:/toyapp/test/login.do";
		}
		
		log.info("[OUT]DashboardController dashboard");
		
		return "home"; 
	}
}
