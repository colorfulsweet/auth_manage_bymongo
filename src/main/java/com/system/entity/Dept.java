package com.system.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.system.service.annotation.CriteriaField;
import com.system.service.annotation.CriteriaType;
/**
 * 组织机构类
 * @author 结发受长生
 *
 */
@JSONType(ignores={"hasChild","parentDept","childDept"})
@Entity("s_dept")
public class Dept implements Serializable,Comparable<Dept> {
	private static final long serialVersionUID = -4450844426367095228L;
	
	@Id
	private ObjectId id;
	private String deptCode;//部门编码
	
	@JSONField(name="text")
	private String deptName;//部门名称
	private String remark;
	
	@CriteriaField(CriteriaType.EQ)
	private String parentId;
	private int deptIndex;
	
	public String getId() {
		//getter方法的返回值改为String是为了组织机构树的懒加载需要
		//使树节点的node对象中的id属性是字符串
		return id.toHexString();
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getDeptIndex() {
		return deptIndex;
	}
	public void setDeptIndex(int deptIndex) {
		this.deptIndex = deptIndex;
	}
	
	@Override
	public int compareTo(Dept target) {
		return this.deptIndex - target.deptIndex;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
