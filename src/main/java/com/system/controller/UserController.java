package com.system.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSFile;
import com.system.entity.User;
import com.system.service.ISystemService;
import com.system.service.dao.IMongoDao;
import com.system.util.ReflectUtils;
import com.system.util.SystemMessage;

@Controller
@RequestMapping(value="/user")
public class UserController {
	private static Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private IMongoDao mongoDao;
	
	@Autowired
	private GridFS girdFS;
	
	@Autowired
	private ISystemService systemService;
	
	/**
	 * 创建/修改 用户
	 * @param user
	 * @return 执行结果信息JSON
	 */
	@RequestMapping(value="/save",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveUser(User user){
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		if(user.getStatus() == null) {
			user.setStatus(false);
		}
		if(user.getId()!=null){
			//修改
			User destUser = mongoDao.get(User.class, user.getId());
			try {
				ReflectUtils.transferFields(user, destUser);
			} catch (Exception e) {
				log.error("实体对象属性映射错误!", e);
				return SystemMessage.getMessage("failed");
			}
			mongoDao.update(destUser);
		} else {
			//新增
			user.setCreateTime(new Date());
			mongoDao.save(user);
		}
		return SystemMessage.getMessage("success");
	}
	/**
	 * 删除用户
	 * @param user
	 * @return 执行结果信息JSON
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delUser(User user){
		mongoDao.delete(user);
		return SystemMessage.getMessage("deleteSuccess");
	}
	/**
	 * 批量删除用户
	 * @param ids
	 * @return 执行结果信息JSON
	 */
	@RequestMapping(value="/deleteUsers",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delUsers(@RequestParam(value="userId")ObjectId[] ids){
		if(ids==null || ids.length==0){
			return SystemMessage.getMessage("failed");
		}
		systemService.delUsers(ids);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 上传头像
	 * @param httpSession
	 * @param iconFile
	 * @return 执行结果信息JSON
	 */
	@RequestMapping(value="/uploadIcon",method=RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public String uploadIcon(HttpSession httpSession,@RequestParam("iconFile") MultipartFile iconFile){
		User user = (User) httpSession.getAttribute("user");
		try{
			try(InputStream input = iconFile.getInputStream()){
				//try-with-resources语法,自动调用资源的close方法
				GridFSFile file = girdFS.createFile(input);
				file.save();
				user.setIconFileId((ObjectId)file.getId());
				mongoDao.update(user);
			}
			return SystemMessage.getMessage("uploadSuccess");
		} catch (IOException e) {
			log.error("文件上传错误!", e);
			return SystemMessage.getMessage("uploadFailed");
		} 
	}
	/**
	 * 获取当前用户的头像
	 * @param session
	 * @param response
	 */
	@RequestMapping(value="/getIcon",method=RequestMethod.GET)
	public void getUserIcon(HttpSession session,HttpServletResponse response){
		User user = (User) session.getAttribute("user");
		try{
			systemService.outputIcon(user, response.getOutputStream());
		} catch (IOException | SQLException e) {
			log.error("获取用户头像失败!", e);
		}
	}
}
