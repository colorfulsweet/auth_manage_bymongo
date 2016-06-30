package com.system.service.dao;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;

import com.mongodb.WriteResult;
import com.system.tags.Page;

public interface IMongoDao {
	/**
	 * 根据ID获取实体对象
	 * @param cls
	 * @param id
	 * @return 实体对象
	 */
	public <T> T get(Class<T> cls, ObjectId id);
	/**
	 * 新增(保存)
	 * @param item
	 * @return 新增数据的ID
	 */
	public Key<Object> save(Object item);
	/**
	 * 更新
	 * @param item
	 * @return 写入数据的结果
	 */
	public WriteResult update(Object item);
	/**
	 * 保存或更新
	 * @param item
	 * @return 写入数据的结果或新增数据的ID
	 */
	public Object saveOrUpdate(Object item);
	/**
	 * 合并
	 * @param item
	 * @return 被合并数据的ID
	 */
	public Key<Object> merge(Object item);
	/**
	 * 删除
	 * @param item
	 * @return 删除数据的执行结果
	 */
	public WriteResult delete(Object item);
	/**
	 * 批量删除
	 * @param items
	 */
	public <T> void deleteAll(Class<T> cls, ObjectId... keys);
	/**
	 * 按照指定条件批量删除
	 * @param cls
	 * @param criteriaMap
	 */
	public <T> void deleteAll(Class<T> cls, Map<String,Object> criteriaMap);
	/**
	 * 查询全集
	 * @param cls
	 * @param criteria 查询条件
	 * @return
	 */
	public <T> List<T> dir(Class<T> cls, Map<String,Object> criteriaMap);
	/**
	 * 分页查询
	 * @param cls
	 * @param page
	 * @param criteria 查询条件
	 */
	public <T> void dir(Class<T> cls,Page page, Map<String,Object> criteriaMap);
}
