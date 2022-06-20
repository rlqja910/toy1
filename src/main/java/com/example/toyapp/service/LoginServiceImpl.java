package com.example.toyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.toyapp.dao.KakaoUserInfoReq;
import com.example.toyapp.dao.KakaoUserInfoRes;
import com.example.toyapp.mapper.LoginMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private LoginMapper loginMapper;

	@Override
	public int updateKakaoUser(KakaoUserInfoReq kakaoUserInfoReq, String userIp) {
		//테이블 서치
		KakaoUserInfoRes userInfo= loginMapper.selectKakaoUser(kakaoUserInfoReq.getUserId());
		System.out.println(userInfo);
		
		if(userInfo != null) {
			System.out.println("계정존재 - 업데이트시작");
			kakaoUserInfoReq.setIpAdr(userIp);
			kakaoUserInfoReq.setEmailAdr(kakaoUserInfoReq.getEmailAdr());
			try{
				loginMapper.updateKakaoUser(kakaoUserInfoReq);
				return 1;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0;
			}
		}else if(userInfo == null){
			System.out.println("계정미존재 - 생성시작");
			kakaoUserInfoReq.setIpAdr(userIp);
			kakaoUserInfoReq.setEmailAdr(kakaoUserInfoReq.getEmailAdr());
			try {
				loginMapper.insertKakaoUser(kakaoUserInfoReq);
				return 1;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0;
			}
		}
		return 0;
	}

}
