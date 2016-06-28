package com.system.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
/**
 * 用户实体类
 * @author 结发受长生
 *
 */
@JSONType(ignores={"icon"})
@Entity("s_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1219825686542965398L;
	@Id
	private ObjectId id;//主键
	private String username;//用户名
	private String realName;//昵称
	private ObjectId iconFileId;//头像
	private String password;//密码(SHA256)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	private String email;//邮箱
	private String tel;//电话
	private Boolean status;//状态
	@Reference
	private Role role;//用户角色
	
	public User(){}
	public User(String username,String password) {
		this.username = username;
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public ObjectId getIconFileId() {
		return iconFileId;
	}
	public void setIconFileId(ObjectId iconFileId) {
		this.iconFileId = iconFileId;
	}
}
