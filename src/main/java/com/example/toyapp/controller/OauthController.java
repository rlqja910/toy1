package com.example.toyapp.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.toyapp.core.util.HttpRequestHelper;
import com.example.toyapp.dao.KakaoUserInfoReq;
import com.example.toyapp.service.LoginService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = { "${base-path}/oauth" })
public class OauthController {

	@Autowired
	private LoginService loginService;
	
	//계정입력후 로그인버튼 누른뒤 타는 서비스
	@RequestMapping(value = { "/kakaoLogin" })
	public String kakaoLogin(HttpServletRequest request, Model model, String code) {
		
		log.info("[IN]OauthController kakaoLogin");

		System.out.println(code);
		
		String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=21af897c92b7e373a0a40551c972388a");
            sb.append("&redirect_uri=http://localhost:8080/toyapp/oauth/kakaoLogin");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            
            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
            request.getSession().setAttribute("accessToken", access_Token);
            request.getSession().setAttribute("refreshToken", refresh_Token);
            
            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
            
            br.close();
            bw.close();
            
            KakaoUserInfoReq responseResult = createKakaoUser(access_Token,request);
            
            model.addAttribute("loginUser", responseResult);
            model.addAttribute("token", access_Token);
            
            HttpRequestHelper.successWrapperJson(model);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        log.info("[OUT]OauthController kakaoLogin");
		
		return "redirect:/toyapp/dashboard/main";
	}
	
	//로그아웃 서비스
		@RequestMapping(value = { "/kakaoLogout" })
		public String kakaoLogout(HttpServletRequest request, Model model) throws IOException  {

			log.info("[IN]OauthController kakaoLogout");
			
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
//	            
//	            log.info("[OUT]OauthController kakaoLogout");
//	            
//	            return "redirect:/toyapp/test/login.do?logoutYn=Y";
//	        }catch(IOException e) {
//	        	e.printStackTrace();
//	        }
	        
	        log.info("[OUT]OauthController kakaoLogout");
	        
	        return "redirect:/toyapp/dashboard/main";
		}
	
	 KakaoUserInfoReq createKakaoUser(String token, HttpServletRequest request) {
		
		log.info("[IN]OauthController createKakaoUser");
		
		String reqURL = "https://kapi.kakao.com/v2/user/me";

	    //access_token을 이용하여 사용자 정보 조회
	    try {
	       URL url = new URL(reqURL);
	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	       conn.setRequestMethod("POST");
	       conn.setDoOutput(true);
	       conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

	       //결과 코드가 200이라면 성공
	       int responseCode = conn.getResponseCode();
	       System.out.println("responseCode : " + responseCode);

	       //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	       String line = "";
	       String result = "";

	       while ((line = br.readLine()) != null) {
	           result += line;
	       }
	       System.out.println("response body : " + result);

	       //Gson 라이브러리로 JSON파싱
	       JsonParser parser = new JsonParser();
	       JsonElement element = parser.parse(result);

	       int id = element.getAsJsonObject().get("id").getAsInt();
	       boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
	       String email = "";
	       String ageRange = "";
	       String birthDate = "";
	       String gender = "";
	       if(hasEmail){
	    	   if(element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email") != null) {
		           email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
		       
	    	   }
	    	  }
	       String nickName = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
	       String userImg = element.getAsJsonObject().get("properties").getAsJsonObject().get("profile_image").getAsString();
	       if(element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("age_range") != null) {
	    	   ageRange = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("age_range").getAsString();
	       
    	   }
	       if(element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("gender") != null) {
		       gender = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("gender").getAsString();
    	   }
	       if(element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("birthday") != null) {
		       birthDate = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("birthday").getAsString();
	       
    	   }
	       
	       System.out.println("-0-0"); 
	       System.out.println(userImg);
	       
	       ///////////////////////////////////////////////////////
	       //db에 로그인정보 업데이트, 로그인 정보 세션 생성
	       
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
			
			kakaoUserInfoReq.setUserId("kakao_" + id);
			kakaoUserInfoReq.setUserNm(nickName);
			kakaoUserInfoReq.setEmailAdr(email);
			kakaoUserInfoReq.setProfile(userImg);
			kakaoUserInfoReq.setAgeRange(ageRange);
			kakaoUserInfoReq.setBirthDate(birthDate);
			kakaoUserInfoReq.setGender(gender);
			
			int result2 = loginService.updateKakaoUser(kakaoUserInfoReq, userIp);
			
			System.out.println(result2);
			
			//로그인 세션 생성
			HttpSession session = request.getSession();
			if(result2 == 1) {
				System.out.println("로그인 유저정보 세션 생성됨");
				session.setAttribute("loginUser", kakaoUserInfoReq);
			}
			
			///////////////////////////////////////////////

	       br.close();
	       
	       log.info("[OUT]OauthController createKakaoUser");
	       
	       return kakaoUserInfoReq;

	       } catch (IOException e) {
	            e.printStackTrace();
	       }
	    
	    log.info("[OUT]OauthController createKakaoUser");
	    
		return null;
	}
}
