package com.logging.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class HttpLogEntry implements Serializable {
	private static final long serialVersionUID = 470106554328528980L;
	
	@Id
	private ObjectId id;
	private Date createDate;//记录的时间
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
