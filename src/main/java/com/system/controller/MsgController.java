package com.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.entity.Msg;
import com.system.service.dao.IMongoDao;
import com.system.util.SystemMessage;

@Controller
@RequestMapping(value="/msg")
public class MsgController {
	@Autowired
	private IMongoDao mongoDao;
	
	@RequestMapping(value="/save",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveRole(Msg msg){
		mongoDao.saveOrUpdate(msg);
		return SystemMessage.getMessage("success");
	}
	
	@RequestMapping(value="/delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delRole(Msg msg){
		mongoDao.delete(msg);
		return SystemMessage.getMessage("deleteSuccess");
	}
}
