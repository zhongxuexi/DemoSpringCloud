/**
* BaseService.java
* Created at 2017年11月14日
* Created by 胡锐锋
* Copyright (C) 2017 SAIC VOLKSWAGEN, All rights reserved.
*/
package com.jess.commons.service;

import javax.servlet.http.HttpServletRequest;

import com.jess.commons.dao.BaseDao;
import com.jess.commons.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

/**
* <p>ClassName: BaseService</p>
* <p>Description: 基础业务类</p>
* <p>Author: 胡锐锋</p>
* <p>Date: 2017年11月14日</p>
*/
public class BaseService<T> extends BaseDao<Mapper<T>, T> {
	@Autowired
	protected HttpServletRequest request;

	/**
	 * 
	 * @Title <p>Title: 获取当前用户的uid</p> 
	 * @Description <p>Description:获取当前用户的uid </p> 
	 * @return uid String
	 */
	public String getCurrentUid() {
		String uid = request.getHeader("uid");
		return uid;
	}

	/**
	 * 新增一个实体（插入所有字段），null的属性也会保存，不会使用数据库默认值 （会插入null）
	 * @param entity 插入的对象，自动组合请求头中公共参数
	 * @return 影响行数 ，正常返回1
	 */
	@Override
	public int insert(T entity) {
		EntityUtils.setCreateInfo(entity);
		return mapper.insert(entity);
	}

	/**
	 * 
	 * @Title 保存一个实体，null的属性不会保存，会使用数据库默认值 (不会插入null)
	 * @param entity 实体，自动组合请求头中公共参数
	 * @return 影响行数 ，正常返回1
	 */
	@Override
	public int insertSelective(T entity) {
		EntityUtils.setCreateInfo(entity);
		return mapper.insertSelective(entity);
	}

	/**
	 * 对象修改（带主键）
	 * @param entity 条件（必须带主键值）和更新内容 ，自动组合请求头中公共参数
	 * @return 影响行数 ，正常返回1
	 */
	@Override
	public int updateById(T entity) {
		EntityUtils.setUpdatedInfo(entity);
		return mapper.updateByPrimaryKey(entity);
	}

	/**
	 * 
	 * @Title <p>Title:根据有主键字段内容的对象（作为where id=?条件）更新内容 </p> 
	 * @Description <p>Description:精确更新对象（过滤空字段） </p> 
	 * @param entity 更新条件和内容对象，自动组合请求头中公共参数
	 * @return 影响行数，正常返回1
	 */
	@Override
	public int updateSelectiveById(T entity) {
		EntityUtils.setUpdatedInfo(entity);
		return mapper.updateByPrimaryKeySelective(entity);
	}
	
	/**
	 * 
	 * @Title <p>Title: 不附加条件的修改</p> 
	 * @Description <p>Description: </p> 
	 * @param entity 更新条件和对象
	 * @return
	 */
	public int updateByPKSelective(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	/**
	 * 
	 * @Title <p>Title: 根据可变条件修改</p> 
	 * @Description 
	 * 	Example example = new Example(TtRegionSetting.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("uid", uid);
		updateByExampleSelective(TtRegionSetting.builder().status("0").build(), example);
	 * @param entity 
	 * @param example
	 * @return
	 */
	public int updateByExampleSelective(T entity, Object example) {
		EntityUtils.setUpdatedInfo(entity);
		return mapper.updateByExampleSelective(entity, example);
	}

	/**
	 * 
	 * @Title <p>Title: 根据主键逻辑删除（必须带主键）</p> 
	 * @Description <p>Description: </p> 
	 * @param entity 带主键字段信息的实体
	 * @return
	 */
	public int deleteLogicById(T entity) {
		EntityUtils.setLogicDeleteInfo(entity);
		return mapper.updateByPrimaryKeySelective(entity);
	}

}
