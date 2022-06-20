package com.example.toyapp.service;

import java.util.List;

import com.example.toyapp.dao.TestDao;

public interface TestService {

	List<TestDao> selectUserId();
}
