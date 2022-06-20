package com.example.toyapp.dao;

import lombok.Data;

@Data
public class KakaoUserInfoReq {

	private String userId;

	private String userNm;

	private String emailAdr;

	private String ipAdr;

	private String profile;

	private String lastLoginDt;

	private String chgDt;

	private String regDt;
	
	private String ageRange;
	
	private String birthDate;
	
	private String gender;

}
