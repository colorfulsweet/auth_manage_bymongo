package com.system.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("s_auth")
public class Auth implements Serializable {
	private static final long serialVersionUID = 909792202776375312L;
	
	@Id
	private ObjectId id;
	private String url;//对该url地址的访问权限
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
