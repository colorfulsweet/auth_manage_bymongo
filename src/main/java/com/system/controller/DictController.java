package com.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.entity.Dict;
import com.system.entity.Dict.DictClause;
import com.system.service.IDictService;
import com.system.service.dao.IMongoDao;
import com.system.util.SystemMessage;
/**
 * 数据字典模块相关功能控制器
 * @author 结发受长生
 *
 */
@Controller
@RequestMapping(value="/dict")
public class DictController {
	
	@Autowired
	private IDictService dictService;
	
	@Autowired
	private IMongoDao mongoDao;
	/**
	 * 根据字典ID获取字典项
	 * @param dict
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/dictClause")
	public String getDictClause(Dict dict,Model model){
		List<DictClause> dictClauses = dictService.getDictClauseList(dict);
		model.addAttribute("dictClauseList", dictClauses);
		model.addAttribute("dict",dict);
		return "WEB-INF/views/dict/dict_clause.jsp";
	}
	/**
	 * 保存字典项
	 * @param dictId
	 * @param dictClause
	 * @return
	 */
	@RequestMapping(value="/saveClause",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveDictClause(@RequestParam("dictId")ObjectId dictId, DictClause dictClause){
		dictService.saveDictClause(dictId,dictClause);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 删除字典项
	 * @param dictId
	 * @param dictClause
	 * @return
	 */
	@RequestMapping(value="/delClause",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delClause(@RequestParam("dictId")ObjectId dictId, DictClause dictClause){
		Dict dict = mongoDao.get(Dict.class, dictId);
		List<DictClause> clauses = dict.getClauses();
		clauses.remove(dictClause);
		mongoDao.update(dict);
		mongoDao.delete(dictClause);
		return SystemMessage.getMessage("deleteSuccess");
	}
	/**
	 * 保存数据字典
	 * @param dict
	 * @return
	 */
	@RequestMapping(value="/save",produces="text/html;charset=utf-8")
	@ResponseBody
	public String saveDict(Dict dict){
		//验证字典编码是否已存在
		Map<String, Object> criteriaMap = new HashMap<String, Object>();
		criteriaMap.put("dictCode", dict.getDictCode());
		if(!mongoDao.dir(Dict.class, criteriaMap).isEmpty()){
			return SystemMessage.getMessage("nameExist");
		}
		mongoDao.saveOrUpdate(dict);
		return SystemMessage.getMessage("success");
	}
	/**
	 * 删除数据字典
	 * @param dict
	 * @return
	 */
	@RequestMapping(value="/delete",produces="text/html;charset=utf-8")
	@ResponseBody
	public String delDict(Dict dict){
		dict = mongoDao.get(Dict.class, dict.getId());
		if(dict.getClauses()!=null && !dict.getClauses().isEmpty()) {
			return SystemMessage.getMessage("hasChildNode");
		}
		mongoDao.delete(dict);
		return SystemMessage.getMessage("deleteSuccess");
	}
}
