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

@Controller
@RequestMapping(value="/menu")
public class MenuController {
	
	@Autowired
	private IMongoDao mongoDao;
	
	@Autowired
	private ISystemService systemService;
	
	@RequestMapping(value="/save",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveOrUpdate(Menu menu){
		mongoDao.saveOrUpdate(menu);
		return SystemMessage.getMessage("success");
	}
	
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
	
	@RequestMapping(value="/submenuList")
	public String openSubmenuList(Menu menu,Model model){
		List<Submenu> submenuList = systemService.getSubmenuList(menu);
		model.addAttribute("submenuList", submenuList);
		model.addAttribute("menu", menu);
		return "WEB-INF/views/menu/submenu.jsp";
	}
	
	@RequestMapping(value="/saveSubmenu",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveSubmenu(@RequestParam("menuId")ObjectId menuId, Submenu submenu){
		systemService.saveSubmenu(menuId, submenu);
		return SystemMessage.getMessage("success");
	}
	
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
	
	@RequestMapping(value="/menuList")
	public String getMenuList(Role role, Model model){
		List<Menu> menuList = systemService.getMenuList();
		role = mongoDao.get(Role.class, role.getId());
		model.addAttribute("role", role);
		model.addAttribute("_menuList", menuList);
		return "WEB-INF/views/role/role_menu.jsp";
	}
}
