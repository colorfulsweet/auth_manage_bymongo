package com.system.util;

import java.util.Properties;

/**
 * 系统消息工具类, 获取从message.properties加载的消息JSON
 * @author 结发受长生
 *
 */
public class SystemMessage {
	private static SystemMessage instance;
	private Properties msgs;
	/**
	 * 根据消息名称获取预定义的消息内容
	 * @param msgName
	 * @return
	 */
	public static String getMessage(String msgName){
		if(instance == null){
			instance = SpringUtils.getBean(SystemMessage.class);
		}
		return instance.getMsgs().getProperty(msgName);
	}
	
	public Properties getMsgs() {
		return msgs;
	}
	public void setMsgs(Properties msgs) {
		this.msgs = msgs;
	}
}
