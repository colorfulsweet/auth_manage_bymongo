package com.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.entity.Dict;
import com.system.entity.Menu;
import com.system.entity.Msg;
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
	@RequestMapping(value="/menuManage")
	public String menuManage(Model model, Page page, @RequestParam Map<String,Object> criteria){
		CharactorUtils.charactorHandle(criteria);
		page.setLinkUrl("admin/menuManage");
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
	@RequestMapping(value="/roleManage")
	public String roleManage(Model model, Page page, @RequestParam Map<String,Object> criteria){
		CharactorUtils.charactorHandle(criteria);
		page.setLinkUrl("admin/roleManage");
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
	@RequestMapping(value="/userManage")
	public String userManage(Model model, Page page, @RequestParam Map<String,Object> criteria){
		CharactorUtils.charactorHandle(criteria);
		page.setLinkUrl("admin/userManage");
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
	@RequestMapping(value="/dictManage")
	public String dictManage(Model model, Page page, @RequestParam Map<String,Object> criteria){
		CharactorUtils.charactorHandle(criteria);
		page.setLinkUrl("admin/dictManage");
		mongoDao.dir(Dict.class, page, criteria);
		model.addAllAttributes(criteria);
		model.addAttribute("page", page);
		return "/WEB-INF/views/admin/dict_manage.jsp";
	}
	/**
	 * 组织机构管理
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/deptManage")
	public String deptManage(Model model){
		return "/WEB-INF/views/admin/dept_manage.jsp";
	}
	/**
	 * 权限认证管理
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/authManage")
	public String authManage(Model model){
		
		return "/WEB-INF/views/admin/auth_manage.jsp";
	}
	/**
	 * 消息数据管理
	 * @param model
	 * @param page
	 * @param criteria 查询条件
	 * @return
	 */
	@RequestMapping(value="/msgManage")
	public String msgManage(Model model, Page page, @RequestParam Map<String,Object> criteria){
		CharactorUtils.charactorHandle(criteria);
		page.setLinkUrl("admin/msgManage");
		mongoDao.dir(Msg.class, page, criteria);
		model.addAllAttributes(criteria);
		model.addAttribute("page", page);
		return "/WEB-INF/views/admin/msg_manage.jsp";
	}
}
