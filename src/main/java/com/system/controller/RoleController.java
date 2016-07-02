package com.system.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.entity.Menu;
import com.system.entity.Role;
import com.system.entity.User;
import com.system.service.ISystemService;
import com.system.service.dao.IMongoDao;
import com.system.util.SystemMessage;
/**
 * 角色管理模块相关功能控制器
 * @author 结发受长生
 *
 */
@Controller
@RequestMapping(value="/role")
public class RoleController {
	@Autowired
	private IMongoDao mongoDao;
	
	@Autowired
	private ISystemService systemService;
	
	private final String datePettern = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat dateFormat;
	/**
	 * 保存角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/save",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveRole(Role role){
		if(role.getCreateTime() == null){
			role.setCreateTime(new Date());
		}
		mongoDao.saveOrUpdate(role);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 删除角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delRole(Role role){
		mongoDao.delete(role);
		return SystemMessage.getMessage("deleteSuccess");
	}
	/**
	 * 获取角色列表
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/roleList")
	public String getRoleList(User user, Model model){
		List<Role> roleList = systemService.getRoleList();
		user = mongoDao.get(User.class, user.getId());
		model.addAttribute("_user", user);
		model.addAttribute("roleList", roleList);
		return "WEB-INF/views/user/user_role.jsp";
	}
	/**
	 * 保存用户-角色关系
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/saveUserRole",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveUserRole(@RequestParam("userId")ObjectId userId, @RequestParam("roleId")ObjectId roleId) {
		systemService.saveUserRole(userId, roleId);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 保存角色-菜单关系
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value="/saveRoleMenu",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveRoleMenu(@RequestParam("roleId")ObjectId roleId, @RequestParam("menuId")ObjectId[] menuId) {
		Role role = (Role) mongoDao.get(Role.class, roleId);
		Set<Menu> menus = new HashSet<Menu>();
		for(ObjectId id : menuId) {
			Menu menu = new Menu();
			menu.setId(id);
			menus.add(menu);
		}
		role.setMenus(menus);
		mongoDao.update(role);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 日期时间格式化数据绑定
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder){
		if(dateFormat == null){
			dateFormat = new SimpleDateFormat(datePettern);
		}
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
