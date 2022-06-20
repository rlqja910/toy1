package com.example.toyapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.toyapp.dao.TestDao;

@Mapper
public interface TestMapper {
	
	List<TestDao> selectUserId();
}
