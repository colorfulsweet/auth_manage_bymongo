package com.test;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.test.entity.TestEntity;

public class JsonTest {
	@Test
	public void testToJson(){
		TestEntity entity = new TestEntity();
		String jsonStr = JSON.toJSONString(entity);
		System.out.println(jsonStr);
	}
}
