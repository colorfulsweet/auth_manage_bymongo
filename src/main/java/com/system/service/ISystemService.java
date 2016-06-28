package com.system.service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import org.bson.types.ObjectId;

import com.system.entity.Menu;
import com.system.entity.Role;
import com.system.entity.User;
import com.system.entity.Menu.Submenu;

public interface ISystemService {
	/**
	 * 验证用户名和密码
	 * @param user
	 * @return
	 */
	public User checkUser(User user);
	/**
	 * 批量删除用户
	 * @param ids 需要删除用户的ID构成的数组
	 */
	public void delUsers(ObjectId[] ids);
	/**
	 * 获取用户的头像并从输出流输出
	 * @param userId 用户ID
	 * @param output 字节输出流
	 */
	public void outputIcon(User user, OutputStream output) throws IOException, SQLException;
	/**
	 * 获取角色列表,并将当前用户所属的角色放入user对象当中
	 * @return 系统中所有的角色集合
	 */
	public List<Role> getRoleList();
	/**
	 * 获取菜单列表, 以及指定角色所对应的菜单
	 * @return 系统中所有的菜单集合
	 */
	public List<Menu> getMenuList();
	/**
	 * 保存用户与角色的关系
	 * @param userId 用户ID
	 * @param roleId 角色ID
	 */
	public void saveUserRole(ObjectId userId, ObjectId roleId);
	/**
	 * 获取某个菜单对应的子菜单列表
	 * @param menu
	 * @return 子菜单的List集合
	 */
	public List<Submenu> getSubmenuList(Menu menu);
	/**
	 * 保存子菜单
	 * @param submenu
	 */
	public void saveSubmenu(ObjectId menuId, Submenu submenu);
}
