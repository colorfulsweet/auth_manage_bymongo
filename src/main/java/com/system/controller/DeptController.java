package com.system.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.system.entity.Dept;
import com.system.service.dao.IMongoDao;
import com.system.util.SystemMessage;

@Controller
@RequestMapping(value="/dept")
public class DeptController {
	@Autowired
	private IMongoDao mongoDao;
	/**
	 * 根据查询条件中的parentId获取下级节点
	 * 如果没有该条件, 则获取所有的根节点
	 * @param criteria 查询条件集合
	 * @return 节点信息集合的JSON字符串形式
	 */
	@RequestMapping(value="/getDeptTree",
			method=RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public String getDeptTreeJson(@RequestParam Map<String,Object> criteria){
		if(!criteria.containsKey("parentId")){
			criteria.put("parentId", null);
		}
		List<Dept> result = mongoDao.dir(Dept.class, criteria);
		Collections.sort(result);
		return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
	}
	/**
	 * 保存组织机构树节点
	 * @param dept
	 * @return
	 */
	@RequestMapping(value="/save",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveDept(Dept dept) {
		mongoDao.saveOrUpdate(dept);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 删除组织机构树节点
	 * @param dept
	 * @return
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String deleteDept(Dept dept) {
		Map<String,Object> criteriaMap = new HashMap<String,Object>();
		criteriaMap.put("parentId", dept.getId());
		mongoDao.delAll(Dept.class, criteriaMap);
		mongoDao.del(dept);
		return SystemMessage.getMessage("success");
	}
}
