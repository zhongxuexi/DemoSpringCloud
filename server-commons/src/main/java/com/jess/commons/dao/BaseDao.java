/**
* BaseDao.java
* Created at 2017年11月14日
* Created by 胡锐锋
* Copyright (C) 2017 SAIC VOLKSWAGEN, All rights reserved.
*/
package com.jess.commons.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.common.Mapper;

/**
* <p>ClassName: BaseDao</p>
* <p>Description: 基础DAO类</p>
* <p>Author: 胡锐锋</p>
* <p>Date: 2017年11月14日</p>
*/
public class BaseDao<M extends Mapper<T>, T> {
	@Autowired
	protected M mapper;

	/**
	 * <p>Title: 查询一条数据</p> 
	 * <p>Description:根据对象信息（做条件） 查询一个POJO对象</p> 
	 * @param entity 对象条件内容（where后字段内容）
	 * @return 查询到的对象（只能有一个，多个对象的时候会抛出异常）
	 */
	public T selectOne(T entity) {
		return mapper.selectOne(entity);
	}

	/**
	 * 
	 * @Title <p>Title: 根据主键字段查询</p> 
	 * @param id 主键字段内容
	 * @return 返回一个对象
	 */
	public T selectById(Object id) {
		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * <p>Title: 根据字段查询单表数据</p> 
	 * @param entity 条件对象
	 * @return 结果列表
	 */
	public List<T> selectList(T entity) {
		return mapper.select(entity);
	}

	/**
	 * 查询单表所有数据
	 * @return 对应实体类/表所有内容
	 */
	public List<T> selectListAll() {
		return mapper.selectAll();
	}

	/**
	 * 查询所有对象数量
	 * @return 实体类对应表数据量
	 */
	public int selectCountAll() {
		return mapper.selectCount(null);
	}

	/**
	 * 指定几个字段（条件），查询数量
	 * @param entity 条件
	 * @return 数量
	 */
	public int selectCount(T entity) {
		return mapper.selectCount(entity);
	}

	/**
	 * 新增一个实体（插入所有字段），null的属性也会保存，不会使用数据库默认值 （会插入null）
	 * @param entity 插入的对象
	 * @return 影响行数 ，正常返回1
	 */
	public int insert(T entity) {
		return mapper.insert(entity);
	}

	/**
	 * 
	 * @Title 保存一个实体，null的属性不会保存，会使用数据库默认值 (不会插入null)
	 * @param entity 实体
	 * @return 影响行数 ，正常返回1
	 */
	public int insertSelective(T entity) {
		return mapper.insertSelective(entity);
	}

	/**
	 * 根据条件删除删除数据
	 * @param entity 条件
	 * @return 影响行数 ，正常返回1
	 */
	public int delete(T entity) {
		return mapper.delete(entity);
	}

	/**
	 * 根据主键id删除
	 * @param id 主键值
	 * @return 影响行数 ，正常返回1
	 */
	public int deleteById(Object id) {
		return mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 对象修改（带主键）
	 * @param entity 条件（必须带主键值）和更新内容
	 * @return 影响行数 ，正常返回1
	 */
	public int updateById(T entity) {
		return mapper.updateByPrimaryKey(entity);
	}

	/**
	 * 
	 * @Title <p>Title:根据有主键字段内容的对象（作为where id=?条件）更新内容 </p> 
	 * @Description <p>Description:精确更新对象（过滤空字段） </p> 
	 * @param entity 更新条件和内容对象
	 * @return 影响行数，正常返回1
	 */
	public int updateSelectiveById(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);

	}

	/**
	 * <p>Title: 组合条件查询单表</p> 
	 * <p>Description: 
	 * <p>使用举例（Service类方法中使用）：
	Example example = new Example(XX.class);    
	Criteria criteria = example.createCriteria();    
	criteria.andEqualTo("field","val");    
	example.setOrderByClause("username asc,email desc");    
	List<?> list = selectByExample(example);    
	相当于：
	select * from user where username = 'joe' and username is null order by username asc,email desc    
	 * </p></p> 
	 * @param example 手动组合条件 （可以组合非"="条件）
	 * @return 数据列表
	 * 
	 */
	public List<T> selectByExample(Object example) {
		return mapper.selectByExample(example);
	}
	
	/**
	 * 
	 * <p>Title: 组合条件查询单表数目</p> 
	 * @param example 组合条件
	 * @return 数量
	 * 
	 */
	public int selectCountByExample(Object example) {
		return mapper.selectCountByExample(example);
	}

}
