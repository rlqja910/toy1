package com.example.toyapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.toyapp.core.util.HttpRequestHelper;
import com.example.toyapp.dao.KakaoUserInfoReq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = { "${base-path}/profile" })
public class ProfileController {
	
	@RequestMapping(value = { "/profile.do" })
	public String profile(HttpServletRequest request, Model model) {
		
		log.info("[IN]ProfileController profile");

		Map<String, Object> returnMap = new HashMap<String, Object>();

//		returnMap.put("success", result);
		
		model.addAttribute("loginUser", (KakaoUserInfoReq) request.getSession().getAttribute("loginUser"));

		HttpRequestHelper.successWrapperJson(returnMap, model);
		
		log.info("[OUT]ProfileController profile");
		
		return "profile"; 
		
	}
}
