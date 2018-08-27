/**
* EntityUtils.java
* Created at 2017年11月14日
* Created by 胡锐锋
* Copyright (C) 2017 SAIC VOLKSWAGEN, All rights reserved.
*/
package com.jess.common.util;

import java.lang.reflect.Field;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.jess.common.constants.Const.*;

/**
* <p>ClassName: EntityUtils</p>
* <p>Description: 实体工具类</p>
* <p>Author: 胡锐锋</p>
* <p>Date: 2017年11月14日</p>
*/
public class EntityUtils {

	private EntityUtils() {
		super();
	}

	/**
	 * @Title <p>Title: 设置创建者和更新者信息</p> 
	 * @Description <p>Description: 快速将bean的创建和更新信息附上相关值</p> 
	 * @param entity 实体bean
	 */
	public static <T> void setCreatAndUpdatInfo(T entity) {
		setCreateInfo(entity);
		setUpdatedInfo(entity);
	}

	/**
	 * @Title <p>Title: 设置创建者信息</p> 
	 * @Description <p>Description: 快速将bean的创建人id和创建时间附上相关值</p> 
	 * @param entity 实体bean
	 */
	public static <T> void setCreateInfo(T entity) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String id = "";
		if (request != null) {
			id = request.getHeader(USERID);
		}
		if (StringUtils.isBlank(id)) {
			id = "admin";
		}
		// 默认属性
		String[] fields = { CRTUSER, CRTTIME, IS_VALID, VERSION };
		Field field = ReflectionUtils.getAccessibleField(entity, CRTTIME);
		// 默认值
		Object[] value = null;
		if (field != null && field.getType().equals(Date.class)) {
			if ("java.lang.String"
					.equalsIgnoreCase(ReflectionUtils.getAccessibleField(entity, VERSION).getType().getTypeName())) {
				value = new Object[] { id, new Date(), IS_VALID_SHORT, VERSION_STR };
			} else {
				value = new Object[] { id, new Date(), IS_VALID_SHORT, VERSION_INT };
			}
		}
		// 填充默认属性值
		setDefaultValues(entity, fields, value);
	}

	/**
	 * @Title <p>Title: 设置更新者信息</p> 
	 * @Description <p>Description: 快速将bean的UPDUSER、UPDTIME附上相关值</p> 
	 * @param entity 实体bean
	 */
	public static <T> void setUpdatedInfo(T entity) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String id = "";
		if (request != null) {
			id = request.getHeader(USERID);
		}
		if (StringUtils.isBlank(id)) {
			id = "admin";
		}
		// 默认属性
		String[] fields = { UPDUSER, UPDTIME, IS_VALID, VERSION };
		Field field = ReflectionUtils.getAccessibleField(entity, UPDTIME);
		Object[] value = null;
		if (field != null && field.getType().equals(Date.class)) {
			String versionType = ReflectionUtils.getTypeName(entity,VERSION);
			if ("java.lang.String"
					.equalsIgnoreCase(versionType)) {
				// 版本是String类型（无值，取默认值；有值取值）
				value = setVesionStr(entity, id);
			} else if("int".equalsIgnoreCase(versionType)||"java.lang.Integer".equalsIgnoreCase(versionType)){
				value = setVesionInt(entity, id);
			}else if("short".equalsIgnoreCase(versionType)||"java.lang.Short".equalsIgnoreCase(versionType)){// 版本是short等其他类型 （无值，取默认值；有值取值）
				value = setVesionShort(entity, id);
			}
		}
		// 填充默认属性值
		setDefaultValues(entity, fields, value);
	}
	
	/**
	 * 
	 * @Title <p>Title: 逻辑删除添加公共信息</p> 
	 * @Description <p>Description: </p> 
	 * @param entity 逻辑删除要修改的内容
	 */
	public static <T> void setLogicDeleteInfo(T entity) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String id = "";
		if (request != null) {
			id = request.getHeader(USERID);
		}
		if (StringUtils.isBlank(id)) {
			id = "admin";
		}
		// 默认属性
		String[] fields = { UPDUSER, UPDTIME, IS_VALID };
		Field field = ReflectionUtils.getAccessibleField(entity, UPDTIME);
		Object[] value = null;
		if (field != null && field.getType().equals(Date.class)) {
			value = new Object[] { id, new Date(), IS_UNVALID_SHORT};
		}
		// 填充默认属性值
		setDefaultValues(entity, fields, value);
	}

	/** 
	 * @Title <p>Title: 版本是int或者Integer类型 （无值，取默认值；有值取值）</p> 
	 * @Description <p>Description: </p> 
	 * @param entity
	 * @param id
	 * @return 
	 */
	private static <T> Object[] setVesionInt(T entity, String id) {
		Object[] value;
		String versionVal = String.valueOf(ReflectionUtils.getFieldValue(entity, VERSION));
		if (versionVal.equals("1") || versionVal.equals("0")||versionVal.equals("null")) {
			versionVal = VERSION_STR;
		}
		String isValid = String.valueOf(ReflectionUtils.getFieldValue(entity, IS_VALID));
		if (isValid.equals("1") ||isValid.equals("null")) {
			value = new Object[] { id, new Date(), IS_VALID_SHORT, Integer.valueOf(versionVal) };
		} else {
			value = new Object[] { id, new Date(), Short.valueOf(isValid), Integer.valueOf(versionVal) };
		}
		return value;
	}

	/** 
	 * @Title <p>Title: 版本是short等其他类型 （无值，取默认值；有值取值）</p> 
	 * @Description <p>Description: </p> 
	 * @param entity
	 * @param id
	 * @return 
	 */
	public static <T> Object[] setVesionShort(T entity, String id) {
		Object[] value;
		String versionVal = String.valueOf(ReflectionUtils.getFieldValue(entity, VERSION));
		if (versionVal.equals("1") || versionVal.equals("0")||versionVal.equals("null")) {
			versionVal = VERSION_STR;
		}
		String isValid = String.valueOf(ReflectionUtils.getFieldValue(entity, IS_VALID));
		if (isValid.equals("1") ||isValid.equals("null")) {
			value = new Object[] { id, new Date(), IS_VALID_SHORT, Short.valueOf(versionVal) };
		} else {
			value = new Object[] { id, new Date(), Short.valueOf(isValid), Short.valueOf(versionVal) };
		}
		return value;
	}

	/** 
	 * @Title <p>Title: 版本是String类型（无值，取默认值；有值取值）</p> 
	 * @Description <p>Description: </p> 
	 * @param entity
	 * @param id
	 * @return 
	 */
	public static <T> Object[] setVesionStr(T entity, String id) {
		Object[] value;
		String versionVal = String.valueOf(ReflectionUtils.getFieldValue(entity, VERSION));
		if (StringUtils.isBlank(versionVal)) {
			versionVal = VERSION_STR;
		}
		String isValid = String.valueOf(ReflectionUtils.getFieldValue(entity, IS_VALID));
		if (isValid.equals("1") ||isValid.equals("null")) {
			value = new Object[] { id, new Date(), IS_VALID_SHORT, versionVal };
		} else {
			value = new Object[] { id, new Date(), Short.valueOf(isValid), versionVal };
		}
		return value;
	}

	/**
	 * @Title <p>Title: 依据对象的属性数组和值数组对对象的属性进行赋值</p> 
	 * @param entity 对象
	 * @param fields 属性数组
	 * @param value 值数组
	 */
	private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
		if (fields == null || entity == null || value == null) {
			return;
		}
		for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			if (ReflectionUtils.hasField(entity, field)) {
				ReflectionUtils.invokeSetter(entity, field, value[i]);
			}
		}
	}

	/**
	 * @Title <p>Title: 根据主键属性，判断主键是否值为空</p> 
	 * @Description <p>Description: </p> 
	 * @param entity 实体bean
	 * @param field 字段（主键字段）
	 * @return 主键为空，则返回false；主键有值，返回true
	 */
	public static <T> boolean isPKNotNull(T entity, String field) {
		if (!ReflectionUtils.hasField(entity, field))
			return false;
		Object value = ReflectionUtils.getFieldValue(entity, field);
		return value != null && !"".equals(value);
	}
}