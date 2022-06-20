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
import com.example.toyapp.dao.Friend;
import com.example.toyapp.dao.KakaoUserInfoReq;
import com.example.toyapp.service.LoginService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = { "${base-path}/friend" })
public class FriendController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = { "/friendsList" })
	public String friendsList(HttpServletRequest request, Model model, String code) {
		
		log.info("[IN]FriendController friendsList");
		
		////////////////////////////////////새 세팅
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
            sb.append("&redirect_uri=http://localhost:8080/toyapp/friend/friendsList");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            
            request.getSession().setAttribute("aaa", "aaa");
            
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
            
            //친구목록 가져오기
            System.out.println("친구목록 가져오기'''''''''''''''''''''''''''''''''''''''''");
            String reqURL2 = "https://kapi.kakao.com/v1/api/talk/friends";
            
            try {
                URL url2 = new URL(reqURL2);
                HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
                
                //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
                System.out.println(access_Token+"---");
                conn2.setRequestMethod("GET");
                conn2.setRequestProperty("Authorization", "Bearer " + access_Token);
//                conn2.setDoOutput(true);
                
                //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
//                BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(conn2.getOutputStream()));
                StringBuilder sb2 = new StringBuilder();
//                bw2.write(sb2.toString());
//                bw2.flush();
                
                //    결과 코드가 200이라면 성공
                int responseCode2 = conn2.getResponseCode();
                System.out.println("responseCode : " + responseCode2);
     
                //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
                BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
                String line2 = "";
                String result2 = "";
                
                while ((line2 = br2.readLine()) != null) {
                    result2 += line2; 
                }
                System.out.println("response body : " + result2);
                
                br2.close();
                
                //Gson 라이브러리로 JSON파싱
     	       JsonParser parser2 = new JsonParser();
     	       JsonElement element2 = parser2.parse(result2);
     	       
     	       Friend friendData = new Gson().fromJson(result2, Friend.class);
     	       System.out.println(friendData.toString());
     	       
     	      model.addAttribute("friendData", friendData);
                
//                bw2.close();
            }catch (Exception e) {
				// TODO: handle exception
            	e.printStackTrace();
			}
            
            ////////////////////
            
            KakaoUserInfoReq responseResult = createKakaoUser(access_Token,request);
            
            model.addAttribute("loginUser", responseResult);
            model.addAttribute("token", access_Token);
            
            HttpRequestHelper.successWrapperJson(model);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
		///////////////////////////////////////////
		
		log.info("[OUT]FriendController friendsList");
		
		return "friendsList"; 
		
	}
	
	KakaoUserInfoReq createKakaoUser(String token, HttpServletRequest request) {
		
		log.info("[IN]FriendController createKakaoUser");
		
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
	       
	       log.info("[OUT]FriendController createKakaoUser");
	       
	       return kakaoUserInfoReq;

	       } catch (IOException e) {
	            e.printStackTrace();
	       }
	    
	    log.info("[OUT]FriendController createKakaoUser");
	    
		return null;
	}
}
