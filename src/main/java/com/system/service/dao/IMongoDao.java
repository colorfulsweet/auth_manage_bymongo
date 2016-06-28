package com.system.service.dao;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

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
	 */
	public void save(Object item);
	/**
	 * 更新
	 * @param item
	 */
	public void update(Object item);
	/**
	 * 保存或更新
	 * @param item
	 */
	public void saveOrUpdate(Object item);
	/**
	 * 合并
	 * @param item
	 */
	public void merge(Object item);
	/**
	 * 删除
	 * @param item
	 */
	public void del(Object item);
	/**
	 * 删除(可级联删除子表关联数据)
	 * @param item
	 * @param cascade 是否级联删除
	 */
	public void del(Object item,boolean cascade);
	/**
	 * 批量删除
	 * @param items
	 */
	public <T> void delAll(Class<T> cls, ObjectId... keys);
	/**
	 * 按照指定条件批量删除
	 * @param cls
	 * @param criteriaMap
	 */
	public <T> void delAll(Class<T> cls, Map<String,Object> criteriaMap);
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
