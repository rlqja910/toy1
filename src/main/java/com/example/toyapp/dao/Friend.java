package com.example.toyapp.dao;

import lombok.Data;

@Data
public class Friend {

	private KakaoUser[] elements;

	private String total_count;

	private String after_url;

	private String favorite_count;
}
