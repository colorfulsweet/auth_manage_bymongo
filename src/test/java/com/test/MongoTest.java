package com.test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.mongodb.morphia.query.Query;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSFile;
import com.system.entity.Menu;
import com.system.entity.Role;
import com.system.entity.User;



public class MongoTest extends UnitTestBase {
	@Test
	public void save(){
		User user = new User();
		user.setUsername("测试用户");
		Role role = new Role();
		role.setRoleName("测试角色");
		Menu menu = new Menu();
		menu.setMenuName("测试菜单");
		Set<Menu> menus = new HashSet<Menu>();
		menus.add(menu);
		
		role.setMenus(menus);
		user.setRole(role);
		
		ds.save(menu);
		ds.save(role);
		ds.save(user);
	}
	@Test
	public void query(){
		Query<Role> query2 = ds.find(Role.class);
		for(Role menu : query2.asList()) {
			System.out.println(menu.getId());
		}
	}
	@Test
	public void findFile() throws IOException{
		GridFS myFS = new GridFS(db,"s_user");
		GridFSFile file = myFS.findOne(new ObjectId("576f4344cc7a13502ef24c59"));
		//当找不到文件的时候, file是null
		if(file != null) {
			System.out.println(file);
		}
	}
	
	@Test
	public void saveFile() throws IOException{
		//5766011340867.png
		GridFS myFS = new GridFS(db,"s_user_icon");
		GridFSFile file = myFS.createFile(new File("/Users/Sookie/Downloads/5766011340867.png"));
		file.save();
	}
	
}
