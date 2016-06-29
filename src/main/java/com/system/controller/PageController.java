package com.system.controller;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.system.entity.Dict;
import com.system.entity.Menu;
import com.system.entity.Role;
import com.system.entity.User;
import com.system.service.ISystemService;
import com.system.service.dao.IMongoDao;

@Controller
public class PageController {
	@Autowired
	private ISystemService systemService;
	
	@Autowired
	private IMongoDao mongoDao;
	
	@RequestMapping({"/","/index"})
	public String toIndex(HttpSession session) {
		if(session.getAttribute("user") == null) {
			return "WEB-INF/views/login.jsp";
		} else {
			return "WEB-INF/views/explorer.jsp";
		}
	}
	@RequestMapping(value="/page/welcome")
	public String toWelcome() {
		return "WEB-INF/views/welcome.jsp";
	}
	/**
	 * 登陆请求
	 * @param user 用户实体对象
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/page/login",method=RequestMethod.POST)
	public String login(User user,Model model,HttpSession session){
		user = systemService.checkUser(user);
		if(user != null && user.getStatus()){
			//对菜单列表按照INDEX进行排序
			session.setAttribute("user", user);
			//排除当前用户没有角色 或者当前角色无任何权限的情况
			if(user.getRole()!=null && user.getRole().getMenus()!=null){
				Set<Menu> menuSet = new TreeSet<Menu>(user.getRole().getMenus());
				user.getRole().setMenus(menuSet);
				session.setAttribute("menuList", user.getRole().getMenus());
			}
			return "redirect:../index";
		} else {
			if(user == null){
				model.addAttribute("info", "用户名/密码错误");
			} else {
				model.addAttribute("info", "该用户已被禁用");
			}
			return "WEB-INF/views/login.jsp";
		}
	}
	/**
	 * 注销请求
	 * @param session
	 * @return 重定向至首页
	 */
	@RequestMapping(value="/page/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:../index";
	}
	/**
	 * 新增/编辑用户请求
	 * @param user 只包含主键ID
	 * @param model
	 * @return 新增用户页面
	 */
	@RequestMapping(value="/page/addOrUpdateUser")
	public String addOrUpdateUser(User user,Model model){
		if(user.getId() != null){
			user = (User) mongoDao.get(User.class, user.getId());
			model.addAttribute("user_", user);
		}
		return "WEB-INF/views/user/add_user.jsp";
	}
	
	/**
	 * 新增/编辑数据字典请求
	 * @param dict 只包含主键ID
	 * @param model
	 * @return 新增字典界面
	 */
	@RequestMapping(value="/page/addOrUpdateDict")
	public String addOrUpdateDict(Dict dict,Model model){
		if(dict.getId() != null){
			dict = (Dict) mongoDao.get(Dict.class, dict.getId());
			model.addAttribute("dict", dict);
		}
		return "WEB-INF/views/dict/add_dict.jsp";
	}
	/**
	 * 新增/编辑菜单请求
	 * @param menu 只包含主键ID
	 * @param model
	 * @return 新增菜单界面
	 */
	@RequestMapping(value="/page/addOrUpdateMenu")
	public String addOrUpdateMenu(Menu menu,Model model){
		if(menu.getId() != null){
			menu = (Menu) mongoDao.get(Menu.class,menu.getId());
			model.addAttribute("menu", menu);
		}
		return "WEB-INF/views/menu/add_menu.jsp";
	}
	/**
	 * 新增/编辑角色请求
	 * @param role 只包含主键ID
	 * @param model
	 * @return 新增角色界面
	 */
	@RequestMapping(value="/page/addOrUpdateRole")
	public String addOrUpdateRole(Role role,Model model){
		if(role.getId() != null){
			role = (Role) mongoDao.get(Role.class,role.getId());
			model.addAttribute("role", role);
		}
		return "WEB-INF/views/role/add_role.jsp";
	}
	
	@RequestMapping(value="/page/personalConfig")
	public String userPersonalConfig(){
		return "WEB-INF/views/user/user_config.jsp";
	}
}
