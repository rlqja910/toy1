package com.example.toyapp.core.util;

import java.util.Map;

import org.springframework.ui.Model;

import com.example.toyapp.core.bean.ResponseDto;

public class HttpRequestHelper {

	public static void successWrapperJson(Map<String, Object> map, Model model) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setReturnCode("01");
		map.put("RES", responseDto);
		model.addAttribute("RES", responseDto);
	}

	public static void successWrapperJson(Model model) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setReturnCode("01");
		model.addAttribute("RES", responseDto);
	}
}
