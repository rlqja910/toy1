package com.example.toyapp.core.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.toyapp.core.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("=================interceptor=====================");
		registry.addInterceptor(new LoginInterceptor()) 
		// 인터셉터 체인 순서 
		.addPathPatterns("/**") 
		// 모든 requestURL에 대해 적용 
		.excludePathPatterns( "/toyapp/test/login.do","/toyapp/oauth/**", "/toyapp/assets/**"); 
		
//		registry.addInterceptor(new ExampleInterceptor()) 
//		.order(2) .addPathPatterns("/**") 
//		.excludePathPatterns("/css/**" , "/*.ico" , "/error"); }
	}

}
