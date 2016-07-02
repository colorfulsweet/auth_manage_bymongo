package com.system.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.system.entity.Menu;
import com.system.entity.Menu.Submenu;
import com.system.entity.Role;
import com.system.entity.User;
import com.system.service.dao.IMongoDao;

@Service
public class SystemService implements ISystemService {
	private static final Logger log = Logger.getLogger(SystemService.class);
	@Autowired
	private IMongoDao mongoDao;
	
	@Autowired
	private GridFS girdFS;
	
	@Autowired
	private ServletContext context;
	
	@Override
	public User checkUser(User user) {
		Map<String,Object> criteriaMap = new HashMap<String,Object>();
		criteriaMap.put("username", user.getUsername());
		criteriaMap.put("password",DigestUtils.sha256Hex(user.getPassword()));
		
		List<User> result = mongoDao.dir(User.class, criteriaMap);
		if(!result.isEmpty()){
			user = (User) result.get(0);
		} else {
			user = null;
		}
		return user;
	}
	
	@Override
	public void delUsers(ObjectId[] ids) {
		mongoDao.deleteAll(User.class, ids);
	}
	
	@Override
	public void outputIcon(User user, OutputStream output) throws IOException, SQLException {
		InputStream input = null;
		if(user.getIconFileId() == null){
			input = buildDefualtIconInput();
		} else {
			GridFSDBFile iconFile = girdFS.findOne(user.getIconFileId());
			input = (iconFile == null) ? buildDefualtIconInput() : iconFile.getInputStream();
		}
		try(BufferedInputStream bufferInput = new BufferedInputStream(input);
			BufferedOutputStream bufferOutput = new BufferedOutputStream(output)){
			//try-with-resources可以对多个资源进行管理
			byte[] buf = new byte[2048];
			int len = 0;
			while((len=bufferInput.read(buf)) != -1){
				bufferOutput.write(buf,0,len);
			}
			bufferOutput.flush();
		}
	}
	
	@Override
	public List<Role> getRoleList(){
		List<Role> roleList = mongoDao.dir(Role.class, null);
		return roleList;
	}
	
	@Override
	public List<Menu> getMenuList() {
		List<Menu> result = mongoDao.dir(Menu.class, null);
		return result;
	}
	
	@Override
	public void saveUserRole(ObjectId userId, ObjectId roleId) {
		User user = mongoDao.get(User.class, userId);
		Role role = new Role();
		role.setId(roleId);
		user.setRole(role);
		mongoDao.update(user);
	}
	
	@Override
	public List<Submenu> getSubmenuList(Menu menu) {
		menu = mongoDao.get(Menu.class, menu.getId());
		List<Submenu> submenuList = menu.getChildrenMenu();
		return submenuList;
	}

	@Override
	public void saveSubmenu(ObjectId menuId, Submenu submenu) {
		Menu menu = mongoDao.get(Menu.class, menuId);
		List<Submenu> submenus = menu.getChildrenMenu();
		if(submenus == null) {
			submenus = new ArrayList<Submenu>();
			menu.setChildrenMenu(submenus);
		}
		submenus.add(submenu);
		mongoDao.save(submenu);
		mongoDao.update(menu);
	}
	
	private InputStream buildDefualtIconInput() {
		String imagesFloder = context.getRealPath("images");
		String defaultIconPath = imagesFloder + File.separator + "default_icon.png";
		try {
			return new FileInputStream(defaultIconPath);
		} catch (FileNotFoundException e) {
			log.error("文件未找到:"+defaultIconPath, e);
			return null;
		}
	}
}
