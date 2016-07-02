package com.system.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.entity.Menu;
import com.system.entity.Menu.Submenu;
import com.system.entity.Role;
import com.system.service.ISystemService;
import com.system.service.dao.IMongoDao;
import com.system.util.SystemMessage;
/**
 * 菜单管理模块相关功能控制器
 * @author 结发受长生
 *
 */
@Controller
@RequestMapping(value="/menu")
public class MenuController {
	
	@Autowired
	private IMongoDao mongoDao;
	
	@Autowired
	private ISystemService systemService;
	/**
	 * 保存菜单
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="/save",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveOrUpdate(Menu menu){
		mongoDao.saveOrUpdate(menu);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 删除菜单
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delMenu(Menu menu){
		menu = mongoDao.get(Menu.class, menu.getId());
		if(menu.getChildrenMenu()!=null && !menu.getChildrenMenu().isEmpty()){
			return SystemMessage.getMessage("hasChildNode");
		}
		mongoDao.delete(menu);
		return SystemMessage.getMessage("deleteSuccess");
	}
	/**
	 * 根据菜单ID获取下级子菜单
	 * @param menu
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/submenuList")
	public String openSubmenuList(Menu menu,Model model){
		List<Submenu> submenuList = systemService.getSubmenuList(menu);
		model.addAttribute("submenuList", submenuList);
		model.addAttribute("menu", menu);
		return "WEB-INF/views/menu/submenu.jsp";
	}
	/**
	 * 保存子菜单
	 * @param menuId
	 * @param submenu
	 * @return
	 */
	@RequestMapping(value="/saveSubmenu",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveSubmenu(@RequestParam("menuId")ObjectId menuId, Submenu submenu){
		systemService.saveSubmenu(menuId, submenu);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 删除子菜单
	 * @param menuId
	 * @param submenu
	 * @return
	 */
	@RequestMapping(value="/delSubmenu",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delSubmenu(@RequestParam("menuId")ObjectId menuId, Submenu submenu){
		Menu menu = mongoDao.get(Menu.class, menuId);
		List<Submenu> submenus = menu.getChildrenMenu();
		submenus.remove(submenu);
		mongoDao.update(menu);
		mongoDao.delete(submenu);
		return SystemMessage.getMessage("deleteSuccess");
	}
	/**
	 * 获取所有菜单项并根据角色ID获取对应的菜单
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/menuList")
	public String getMenuList(Role role, Model model){
		List<Menu> menuList = systemService.getMenuList();
		role = mongoDao.get(Role.class, role.getId());
		model.addAttribute("role", role);
		model.addAttribute("_menuList", menuList);
		return "WEB-INF/views/role/role_menu.jsp";
	}
}
