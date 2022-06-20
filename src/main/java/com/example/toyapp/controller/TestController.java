package com.example.toyapp.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.toyapp.core.util.HttpRequestHelper;
import com.example.toyapp.dao.KakaoUserInfoReq;
import com.example.toyapp.dao.TestDao;
import com.example.toyapp.service.TestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = { "${base-path}/test" })
public class TestController {
	
	@Autowired
	private TestService testService;
	
	//로그인화면
	@RequestMapping(value = { "/login.do" })
	public String login(HttpServletRequest request, Model model, String logoutYn) {

		log.info("[IN]TestController login");
		if(logoutYn == null) {
			logoutYn = "N";
		}

		log.info("logoutYn = {}",logoutYn);
		
		System.out.println("==login page=="); 
		
		KakaoUserInfoReq KakaoUserInfoReq = (KakaoUserInfoReq)request.getSession().getAttribute("loginUser");

		if(KakaoUserInfoReq == null && request.getSession().getAttribute("accessToken") != null) {
			String access_Token = (String)request.getSession().getAttribute("accessToken");
	        String reqURL ="https://kapi.kakao.com/v1/user/unlink";
	        
//	        try {
//	            URL url = new URL(reqURL);
//	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	            conn.setRequestMethod("POST");
//	            
//	            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
//	            int responseCode = conn.getResponseCode();
//	            System.out.println("responseCode : " + responseCode);
//	 
//	            if(responseCode ==400)
//	                throw new RuntimeException("카카오 로그아웃 도중 오류 발생");
//	            
//	            
//	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//	            
//	            String br_line = "";
//	            String result = "";
//	            while ((br_line = br.readLine()) != null) {
//	                result += br_line;
//	            }
//	            System.out.println("결과");
//	            System.out.println(result);
//	        }catch (Exception e) {
//				// TODO: handle exception
//	        	e.printStackTrace();
//			}
	        
			request.getSession().removeAttribute("accessToken");
			request.getSession().removeAttribute("refreshToken");
			request.getSession().invalidate(); 
		} 
		
		if(KakaoUserInfoReq != null && logoutYn.equals("Y")) {
			System.out.println("==logout...=="); 
			System.out.println((String)request.getSession().getAttribute("accessToken"));
			System.out.println((KakaoUserInfoReq)request.getSession().getAttribute("loginUser"));
			
			request.getSession().removeAttribute("loginUser");
			request.getSession().removeAttribute("accessToken");
			request.getSession().removeAttribute("refreshToken");
			request.getSession().invalidate();
		}else if(KakaoUserInfoReq != null && logoutYn.equals("N")){
//			return "redirect:/toyapp/dashboard/main";
		}
		
		log.info("[OUT]TestController login");
		
		return "login";
	}

	@RequestMapping(value = { "/select/userId" })
	public @ResponseBody Map<String, Object> getUserIdList(HttpServletRequest request, Model model) {

		log.info("[IN]TestController getUserIdList"); 
		
		List<TestDao> userIdList = testService.selectUserId();

		for(TestDao a : userIdList) {
			System.out.println(a.getUserId());
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();

		returnMap.put("userIdListRes", userIdList);

		HttpRequestHelper.successWrapperJson(returnMap, model);

		log.info("[OUT]TestController getUserIdList");
		
		return returnMap;
	}
}
