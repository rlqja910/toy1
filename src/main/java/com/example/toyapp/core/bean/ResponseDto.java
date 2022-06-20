package com.example.toyapp.core.bean;

import lombok.Data;

@Data
public class ResponseDto {

	private String returnCode;

	private String returnMsg;

	private String errorCode;

	private String errorMsg;

	private String returnUrl;

	private Object data;

}
