package com.system.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 角色实体类
 * @author 结发受长生
 *
 */
@Entity("s_role")
public class Role implements Serializable {
	private static final long serialVersionUID = 639317829778677857L;
	@Id
	private ObjectId id;//主键
	private String roleName;//角色名称
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	private String remark;//备注
	
	@Reference
	private Set<Menu> menus;//该角色对应的菜单
	@Reference
	private Set<Auth> auths;//该角色对应的权限
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Role)){
			return false;
		}
		if(this == obj) {
			return true;
		}
		Role r = (Role) obj;
		if(this.id!=null && this.id.equals(r.id)){
			return true;
		} else {
			return false;
		}
	}
	@Override
	public int hashCode(){
		if(id == null){
			return 0;
		} else {
			return id.hashCode();
		}
	}
	public Set<Auth> getAuths() {
		return auths;
	}
	public void setAuths(Set<Auth> auths) {
		this.auths = auths;
	}
}
