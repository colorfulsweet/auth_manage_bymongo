package com.system.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.entity.Dict;
import com.system.entity.Dict.DictClause;
import com.system.service.dao.IMongoDao;

@Service
public class DictService implements IDictService {
	
	@Autowired
	private IMongoDao mongoDao;
	
	@Override
	public List<DictClause> getDictClauseList(Dict dict) {
		dict = mongoDao.get(Dict.class, dict.getId());
		return dict.getClauses();
	}

	@Override
	public void saveDictClause(ObjectId dictId, DictClause dictClause) {
		Dict dict = mongoDao.get(Dict.class, dictId);
		List<DictClause> clauses = dict.getClauses();
		if(clauses == null) {
			clauses = new ArrayList<DictClause>();
			dict.setClauses(clauses);
		}
		clauses.add(dictClause);
		mongoDao.save(dictClause);
		mongoDao.update(dict);
	}

}
