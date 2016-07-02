package com.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.entity.Msg;
import com.system.service.dao.IMongoDao;
import com.system.util.SystemMessage;
/**
 * 静态消息模块相关功能控制器
 * @author 结发受长生
 *
 */
@Controller
@RequestMapping(value="/msg")
public class MsgController {
	@Autowired
	private IMongoDao mongoDao;
	/**
	 * 保存静态消息
	 * @param msg
	 * @return
	 */
	@RequestMapping(value="/save",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveMsg(Msg msg){
		//验证该名称是否已存在
		Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("name", msg.getName());
		if(!mongoDao.dir(Msg.class, criteriaMap).isEmpty()) {
			return SystemMessage.getMessage("nameExist");
		}
		mongoDao.saveOrUpdate(msg);
		//将新保存的消息加入到消息池当中
		SystemMessage.putMessage(msg);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 删除静态消息
	 * @param msg
	 * @return
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delMsg(Msg msg){
		mongoDao.delete(msg);
		return SystemMessage.getMessage("deleteSuccess");
	}
}
