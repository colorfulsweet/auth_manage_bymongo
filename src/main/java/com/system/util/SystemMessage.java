package com.system.util;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.system.entity.Msg;
import com.system.service.dao.IMongoDao;

/**
 * 系统消息工具类, 获取从message.properties加载的消息JSON
 * @author 结发受长生
 *
 */
@Component
public class SystemMessage {
	private static Properties msgs = new Properties();
	
	@Autowired
	private IMongoDao mongoDao;
	/**
	 * 初始化方法, 从s_msg表读取所有静态消息内容
	 * 并按照 name-json 的键值对加入到Properties
	 */
	@PostConstruct
	public void init(){
		List<Msg> messages = mongoDao.dir(Msg.class, null);
		for(Msg msg : messages) {
			msgs.put(msg.getName(), JSON.toJSONString(msg));
		}
	}
	/**
	 * 根据消息名称获取预定义的消息内容
	 * @param msgName
	 * @return
	 */
	public static String getMessage(String msgName){
		return msgs.getProperty(msgName);
	}
}
