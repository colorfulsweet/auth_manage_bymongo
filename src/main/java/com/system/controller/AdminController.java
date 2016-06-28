package com.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.entity.Dict;
import com.system.entity.Menu;
import com.system.entity.Role;
import com.system.entity.User;
import com.system.service.dao.IMongoDao;
import com.system.tags.Page;
import com.system.util.CharactorUtils;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	private IMongoDao mongoDao;
	/**
	 * 菜单管理
	 * @param model
	 * @param page
	 * @param criteria 查询条件
	 * @return
	 */
	@RequestMapping(value="/menuManage.html")
	public String menuManage(Model model, Page page, @RequestParam Map<String,Object> criteria){
		CharactorUtils.charactorHandle(criteria);
		page.setLinkUrl("admin/menuManage.html");
		mongoDao.dir(Menu.class, page, criteria);
		model.addAllAttributes(criteria);
		model.addAttribute("page",page);
		return "/WEB-INF/views/admin/menu_manage.jsp";
	}
	/**
	 * 角色管理
	 * @param model
	 * @param page
	 * @param criteria 查询条件
	 * @return
	 */
	@RequestMapping(value="/roleManage.html")
	public String roleManage(Model model, Page page, @RequestParam Map<String,Object> criteria){
		CharactorUtils.charactorHandle(criteria);
		page.setLinkUrl("admin/roleManage.html");
		mongoDao.dir(Role.class, page, criteria);
		model.addAllAttributes(criteria);
		model.addAttribute("page", page);
		return "/WEB-INF/views/admin/role_manage.jsp";
	}
	/**
	 * 用户管理
	 * @param model
	 * @param page
	 * @param criteria 查询条件
	 * @return
	 */
	@RequestMapping(value="/userManage.html")
	public String userManage(Model model, Page page, @RequestParam Map<String,Object> criteria){
		CharactorUtils.charactorHandle(criteria);
		page.setLinkUrl("admin/userManage.html");
		mongoDao.dir(User.class, page, criteria);
		model.addAllAttributes(criteria);
		model.addAttribute("page", page);
		return "/WEB-INF/views/admin/user_manage.jsp";
	}
	/**
	 * 字典管理
	 * @param model
	 * @param page
	 * @param criteria 查询条件
	 * @return
	 */
	@RequestMapping(value="/dictManage.html")
	public String dictManage(Model model, Page page, @RequestParam Map<String,Object> criteria){
		CharactorUtils.charactorHandle(criteria);
		page.setLinkUrl("admin/dictManage.html");
		mongoDao.dir(Dict.class, page, criteria);
		model.addAllAttributes(criteria);
		model.addAttribute("page", page);
		return "/WEB-INF/views/admin/dict_manage.jsp";
	}
	
	@RequestMapping(value="/deptManage.html")
	public String deptManage(Model model){
		return "/WEB-INF/views/admin/dept_manage.jsp";
	}
	
	@RequestMapping(value="/authManage.html")
	public String authManage(Model model){
		
		return "/WEB-INF/views/admin/auth_manage.jsp";
	}
	
}
