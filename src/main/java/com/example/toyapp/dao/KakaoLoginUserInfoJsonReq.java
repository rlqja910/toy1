package com.example.toyapp.dao;

import lombok.Data;

@Data
public class KakaoLoginUserInfoJsonReq {

	private String id;

	private String password;

	private String email;

	private String name;

	private String profile;

	private String snsyn;
}
