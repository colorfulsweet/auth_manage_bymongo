package com.system.service.dao;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;
import com.system.service.annotation.CriteriaField;
import com.system.tags.Page;
import com.system.util.ReflectUtils;


@Repository
public class MongoDao implements IMongoDao {
	private static Logger log = Logger.getLogger(MongoDao.class);
	
	@Autowired
	private Morphia morphia;
	
	@Autowired
	private Datastore datastore;
	
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public <T> T get(Class<T> cls, ObjectId id) {
		return datastore.get(cls, id);
	}
	
	@Override
	public Key<Object> save(Object item) {
		return datastore.save(item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WriteResult update(Object item) {
		UpdateOperations<Object> ops = (UpdateOperations<Object>) datastore.createUpdateOperations(item.getClass());
		try {
			ReflectUtils.updateOperationsHandle(item, ops);
			return datastore.update(item, ops).getWriteResult();
		} catch (Exception e) {
			log.error("数据更新失败", e);
			return null;
		}
	}

	@Override
	public Object saveOrUpdate(Object item) {
		if(morphia.getMapper().getId(item) == null) {
			return save(item);
		} else {
			return update(item);
		}
	}
	
	@Override
	public Key<Object> merge(Object item) {
		return datastore.merge(item);
	}
	
	@Override
	public WriteResult delete(Object item) {
		return datastore.delete(item);
	}
/*
	@Override
	public WriteResult delete(Object item, boolean cascade) {
		if(!cascade) {
			return delete(item);
		} else {
			//TODO 获取到对象中的@Reference引用,进行级联删除
			return delete(item);
		}
	}
	*/
	
	@Override
	public <T> void deleteAll(Class<T> cls, ObjectId... keys){
		datastore.delete(cls, keys);
	}
	
	@Override
	public <T> void deleteAll(Class<T> cls, Map<String, Object> criteriaMap) {
		Set<Entry<String,Object>> enties = criteriaMap.entrySet();
		Query<T> query = datastore.createQuery(cls);
		for(Entry<String,Object> entry : enties) {
			query.filter(entry.getKey(), entry.getValue());
		}
		datastore.delete(query);
	}
	
	@Override
	public <T> List<T> dir(Class<T> cls, Map<String, Object> criteriaMap) {
		Query<T> query = datastore.find(cls);
		if(criteriaMap!=null && !criteriaMap.isEmpty()) {
			conditionHandle(cls, query, criteriaMap);
		}
		return query.asList();
	}

	@Override
	public <T> void dir(Class<T> cls, Page page, Map<String, Object> criteriaMap) {
		Query<T> query = datastore.find(cls);
		if(criteriaMap!=null && !criteriaMap.isEmpty()) {
			conditionHandle(cls, query, criteriaMap);
		}
		page.setRowCount(query.countAll());
		query.batchSize(page.getRowStart());
		query.limit(page.getPageSize());
		page.setResult(query.asList());
	}
	/**
	 * 将查询条件添加到Query当中
	 * @param cls 表示实体类的Class对象
	 * @param query 查询基准
	 * @param criteria 查询条件的字段名与字段值的Map集合
	 */
	private <T> void conditionHandle(Class<T> cls, Query<T> query, Map<String,Object> criteriaMap){
		//获取实体类当中所有的属性列表
		Field[] fields = cls.getDeclaredFields();
		Map<String,Field> fieldMap = new HashMap<String,Field>();
		for(Field field : fields) {
			fieldMap.put(field.getName(), field);
		}
		//获取查询条件的列表
		Set<Entry<String, Object>> entries = criteriaMap.entrySet();
		for(Entry<String, Object> entry : entries){
			//将查询条件加入到Criteria
			Criteria criteria = null;
			String key = entry.getKey();
			String value = (String) entry.getValue();
			
			String fieldName = null;
			//针对日期时间数据采集字段名称
			byte dateFlag = 0;
			if(key.contains("Start")){
				fieldName = key.substring(0, key.indexOf("Start"));
				dateFlag = -1;
			} else if(key.contains("End")){
				fieldName = key.substring(0, key.indexOf("End"));
				dateFlag = 1;
			} else {
				fieldName = key;
			}
			if(!fieldMap.containsKey(fieldName)){
				continue;
			}
			if(value == null){
				criteria = query.criteria(fieldName).equal(null);
			} else {
				criteria = createCriterion(query, fieldMap.get(fieldName), value, dateFlag);
			}
			if(criteria != null){
				query.and(criteria);
			}
		}
	}
	/**
	 * 针对某个字段产生特定的查询条件
	 * @param query 查询实例对象
	 * @param field 字段
	 * @param value 查询的条件值
	 * @param dateFlag 日期类型的标记
	 * @return 查询条件Criteria对象
	 */
	private <T> Criteria createCriterion(Query<T> query, Field field, String value, byte dateFlag){
		Criteria criteria = null;
		String fieldName = field.getName();
		if(field.isAnnotationPresent(CriteriaField.class)){
			CriteriaField cf = field.getAnnotation(CriteriaField.class);
			switch(cf.value()){
				case EQ : criteria = query.criteria(fieldName).equal(value);
						break;
				case GT : criteria = query.criteria(fieldName).greaterThan(value);
						break;
				case LT : criteria = query.criteria(fieldName).lessThan(value);
						break;
				case LIKE : criteria = query.criteria(fieldName).contains(value);
						break;
				case I_LIKE : criteria = query.criteria(fieldName).containsIgnoreCase(value);
						break;
				default:
			}
			return criteria;
		}
		try {
			switch(field.getType().getName()){
			case "java.lang.String" :
				criteria = query.criteria(fieldName).contains(value);
				break;
			case "java.sql.Timestamp" :
			case "java.util.Date" :
				//对于日期时间类型的, 使用起止时间进行查询
				if(dateFlag > 0){
					criteria = query.criteria(fieldName).lessThan(dateFormat.parse(value));
				} else if(dateFlag < 0){
					criteria = query.criteria(fieldName).greaterThan(dateFormat.parse(value));
				}
				break;
			case "java.lang.Boolean" :
			case "boolean" :
				criteria = query.criteria(fieldName).equal(Boolean.parseBoolean(value));
				break;
			default : log.warn("未知的数据类型!--" + field.getType().getName());
			}
			
		} catch (ParseException e) {
			log.error("解析查询条件出错!", e);
		}
		return criteria;
	}
	
	
}
