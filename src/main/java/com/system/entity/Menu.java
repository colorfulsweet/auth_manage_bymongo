package com.system.entity;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.alibaba.fastjson.annotation.JSONType;
/**
 * 菜单实体类
 * @author 结发受长生
 *
 */
@JSONType(ignores={"roles"})
@Entity("s_menu")
public class Menu implements Serializable,Comparable<Menu> {
	private static final long serialVersionUID = -2750501428057074001L;
	@Id
	private ObjectId id;//主键
	private String menuName;//菜单名称
	private String url;//URL地址
	private String icon;//图标编码
	private String remark;//备注
	private int menuIndex;
	@Reference
	private List<Submenu> childrenMenu;//子菜单
	/**
	 * 子菜单实体类
	 * @author 结发受长生
	 *
	 */
	@Entity("s_submenu")
	public static class Submenu implements Serializable {
		private static final long serialVersionUID = 2119936011887556337L;
		@Id
		private ObjectId id;
		private String submenuName;//子菜单名称
		private String url;//URL地址
		private String remark;//备注
		public ObjectId getId() {
			return id;
		}
		public void setId(ObjectId id) {
			this.id = id;
		}
		public String getSubmenuName() {
			return submenuName;
		}
		public void setSubmenuName(String submenuName) {
			this.submenuName = submenuName;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<Submenu> getChildrenMenu() {
		return childrenMenu;
	}
	public void setChildrenMenu(List<Submenu> childrenMenu) {
		this.childrenMenu = childrenMenu;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getMenuIndex(){
		return menuIndex;
	}
	public void setMenuIndex(int menuIndex) {
		this.menuIndex = menuIndex;
	}
	@Override
	public int compareTo(Menu obj) {
		if(this.menuIndex > obj.menuIndex) {
			return 1;
		} else if(this.menuIndex < obj.menuIndex) {
			return -1;
		} else {
			return 0;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
