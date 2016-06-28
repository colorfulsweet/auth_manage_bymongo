package com.system.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.system.entity.Dict;
import com.system.entity.Dict.DictClause;

public interface IDictService {
	/**
	 * 获取字典当中的字典项
	 * @param dict
	 * @return
	 */
	public List<DictClause> getDictClauseList(Dict dict);
	/**
	 * 保存字典项(包含字典项序列)
	 * @param dictClause
	 */
	public void saveDictClause(ObjectId dictId, DictClause dictClause);
}
