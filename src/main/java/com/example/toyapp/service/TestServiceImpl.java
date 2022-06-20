package com.example.toyapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.toyapp.dao.TestDao;
import com.example.toyapp.mapper.TestMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestServiceImpl implements TestService{
	
	@Autowired
	private TestMapper testMapper;

	@Override
	public List<TestDao> selectUserId() {
		// TODO Auto-generated method stub
		
		return testMapper.selectUserId();
	}

}
