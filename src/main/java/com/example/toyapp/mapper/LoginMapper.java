package com.example.toyapp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.toyapp.dao.KakaoUserInfoReq;
import com.example.toyapp.dao.KakaoUserInfoRes;

@Mapper
public interface LoginMapper {

	// 카카오계정 유무확인
	KakaoUserInfoRes selectKakaoUser(String id);

	// 카카오계정 생성
	void insertKakaoUser(KakaoUserInfoReq kakaoUserInfoReq);

	// 카카오계정 업데이트
	void updateKakaoUser(KakaoUserInfoReq kakaoUserInfoReq);

}
