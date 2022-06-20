package com.example.toyapp.service;

import com.example.toyapp.dao.KakaoUserInfoReq;

public interface LoginService {

	int updateKakaoUser(KakaoUserInfoReq kakaoUserInfoReq, String userIp);
}
