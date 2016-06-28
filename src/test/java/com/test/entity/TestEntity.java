package com.test.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class TestEntity {
	@JSONField(serialzeFeatures=SerializerFeature.WriteEnumUsingToString)
	private TestEntity2 entity;
	public TestEntity(){
		entity = new TestEntity2("aa","bb");
	}
	public TestEntity2 getEntity() {
		return entity;
	}
	public void setEntity(TestEntity2 entity) {
		this.entity = entity;
	}
}

class TestEntity2 {
	private String field1;
	private String field2;
	public TestEntity2(){}
	public TestEntity2(String field1, String field2) {
		this.field1 = field1;
		this.field2 = field2;
	}
	
	@Override
	public String toString(){
		return "this is TestEntity2";
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
}