/**
* ReflectionUtils.java
* Created at 2017年11月14日
* Created by 胡锐锋
* Copyright (C) 2017 SAIC VOLKSWAGEN, All rights reserved.
*/
package com.jess.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 
 * @Title <p>ClassName: ReflectionUtils</p>
 * @Description <p>Description: 反射工具类</p>
 * @Author <p>Author: 胡锐锋</p>
 * @Date <p>Date: 2017年12月19日</p>
 */
@SuppressWarnings("rawtypes")
public class ReflectionUtils {

	/**
	 * <p>
	 * Field ReflectionUtils.java:提示信息，不可为空
	 * </p>
	 */
	private static final String OBJECT_CAN_T_BE_NULL = "object can't be null";

	private ReflectionUtils() {
		super();
	}

	private static final String SETTER_PREFIX = "set";

	private static final String GETTER_PREFIX = "get";

	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	/**
	 * 
	 * @Title <p>Title: 调用Getter方法. </p> 
	 * @Description <p>Description: 调用Getter方法. 支持多级，如：对象名.对象名.方法</p> 
	 * @param obj 对象名
	 * @param propertyName 属性名
	 * @return 属性对象
	 */
	public static Object invokeGetter(Object obj, String propertyName) {
		Object object = obj;
		for (String name : StringUtils.split(propertyName, ".")) {
			String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
			object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
		}
		return object;
	}

	/**
	 * 
	 * @Title <p>Title: 调用Setter方法</p> 
	 * @Description <p>Description: 仅匹配方法名。 支持多级，如：对象名.对象名.方法</p> 
	 * @param obj 对象
	 * @param propertyName 属性名
	 * @param value 输入参数值
	 */
	public static void invokeSetter(Object obj, String propertyName, Object value) {
		Object object = obj;
		String[] names = StringUtils.split(propertyName, ".");
		for (int i = 0; i < names.length; i++) {
			if (i < names.length - 1) {
				String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
				object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
			} else {
				String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
				invokeMethodByName(object, setterMethodName, new Object[] { value });
			}
		}
	}

	/**
	 * 
	 * @Title <p>Title: 直接读取对象属性值</p> 
	 * @Description <p>Description: 无视private/protected修饰符, 不经过getter函数.</p> 
	 * @param obj 对象
	 * @param fieldName 属性名
	 * @return 属性值
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Object result = null;
		Field field = getAccessibleField(obj, fieldName);
		if (field == null) {
			logger.error("在对象 [{}]中找不到字段 [{}] ", obj, fieldName);
			return result;
		}
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("字段不存在：{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @Title <p>Title: 直接设置对象属性值</p> 
	 * @Description <p>Description: 无视private/protected修饰符, 不经过setter函数.</p> 
	 * @param obj 对象
	 * @param fieldName 属性名
	 * @param value 属性值
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);
		if (field == null) {
			logger.error("在对象 [{}]中" + "找不到字段 [{}] ", obj, fieldName);
			return;
		}
		try {
			field.set(obj, convert(value, field.getType()));
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 
	 * @Title <p>Title: 对象转换成指定类型</p> 
	 * @Description <p>Description: 类型转换</p> 
	 * @param object 对象
	 * @param type 类型
	 * @return 对应类型对象
	 */
	public static Object convert(Object object, Class<?> type) {
		if (object instanceof Number) {
			Number number = (Number) object;
			return convertNumber(object, type, number);
		}
		if (type.equals(String.class)) {
			return object == null ? "" : object.toString();
		}
		return object;
	}

	/**
	 * 
	 * @Title <p>Title: 对象转数值类型</p> 
	 * @Description <p>Description: 对象转数值类型</p> 
	 * @param object 对象
	 * @param type 数值类型
	 * @param number 数值
	 * @return 对象
	 */
	private static Object convertNumber(Object object, Class<?> type, Number number) {
		if (type.equals(byte.class) || type.equals(Byte.class)) {
			return number.byteValue();
		}
		if (type.equals(short.class) || type.equals(Short.class)) {
			return number.shortValue();
		}
		if (type.equals(int.class) || type.equals(Integer.class)) {
			return number.intValue();
		}
		if (type.equals(long.class) || type.equals(Long.class)) {
			return number.longValue();
		}
		if (type.equals(float.class) || type.equals(Float.class)) {
			return number.floatValue();
		}
		if (type.equals(double.class) || type.equals(Double.class)) {
			return number.doubleValue();
		}
		return object;
	}

	/**
	 * 
	 * @Title <p>Title: 直接调用对象方法,无视private/protected修饰符</p> 
	 * @Description <p>Description: （指定参数类型）用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用. 同时匹配方法名+参数类型</p> 
	 * @param obj 对象
	 * @param methodName 方法名
	 * @param parameterTypes 参数类型数组
	 * @param args 参数数组
	 * @return 方法执行结果
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			logger.error("在对象 [{}]中" + "找不到方法[{}] ", obj, methodName);
			return method;
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 
	 * @Title <p>Title: 直接调用对象方法, 无视private/protected修饰符</p> 
	 * @Description <p>Description: （不指定参数类型）用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用; 只匹配函数名，如果有多个同名函数调用第一个。</p> 
	 * @param obj 对象
	 * @param methodName 方法名
	 * @param args 参数数组
	 * @return 方法执行结果
	 */
	public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
		Method method = getAccessibleMethodByName(obj, methodName);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 
	 * @Title <p>Title: 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问</p> 
	 * @Description <p>Description: 如向上转型到Object仍无法找到, 返回null.</p> 
	 * @param obj 对象
	 * @param fieldName 属性名
	 * @return 对象对应类本身的字段
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Validate.notNull(obj, OBJECT_CAN_T_BE_NULL);
		Validate.notBlank(fieldName, "fieldName can't be blank");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field不在当前类定义,继续向上转型
				continue;// new add
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title <p>Title: 取字段类型</p> 
	 * @Description <p>Description: </p> 
	 * @param entity
	 * @param fileName
	 * @return
	 */
	public static <T> String getTypeName(T entity, String fileName) {
		Field field = ReflectionUtils.getAccessibleField(entity, fileName);
		if (field == null || field.getType() == null) {
			return "";
		}
		return field.getType().getTypeName();
	}

	/**
	 * 
	 * @Title <p>Title: 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问</p> 
	 * @Description <p>Description: 如向上转型到Object仍无法找到, 返回null. 匹配函数名+参数类型。用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)</p> 
	 * @param obj 对象
	 * @param methodName 方法名
	 * @param parameterTypes 参数类型数组
	 * @return 获取对应类本身的方法
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		Validate.notNull(obj, OBJECT_CAN_T_BE_NULL);
		Validate.notBlank(methodName, "methodName can't be blank");

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
				.getSuperclass()) {
			try {
				Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
				makeAccessible(method);
				return method;
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
				continue;// new add
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title <p>Title: 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.</p> 
	 * @Description <p>Description: 如向上转型到Object仍无法找到, 返回null. 只匹配函数名。用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)</p> 
	 * @param obj 对象
	 * @param methodName 方法名
	 * @return 方法
	 */
	public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
		Validate.notNull(obj, OBJECT_CAN_T_BE_NULL);
		Validate.notBlank(methodName, "methodName can't be blank");

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
				.getSuperclass()) {
			Method[] methods = searchType.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					makeAccessible(method);
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title <p>Title: 改变private/protected的方法为public</p> 
	 * @Description <p>Description: 尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。</p> 
	 * @param method 方法
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * 
	 * @Title <p>Title: 改变private/protected的成员变量为public</p> 
	 * @Description <p>Description: 尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。</p> 
	 * @param field 字段
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
				|| Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 
	 * @Title <p>Title: 获得Class定义中声明的泛型参数的类型</p> 
	 * @Description <p>Description: 泛型必须定义在父类处 如无法找到, 返回Object.class. </p> 
	 * @param clazz 类型
	 * @return 对应类的类型，不确定就是Object.class
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * 
	 * @Title <p>Title: 获得Class定义中声明的父类的泛型参数的类型. </p> 
	 * @Description <p>Description: 通过反射获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class</p> 
	 * @param clazz 类型
	 * @param index 索引
	 * @return 泛型参数的类型
	 */
	public static Class getClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: {}, Size of {}'s Parameterized Type: {}", index, clazz.getSimpleName(), params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 
	 * @Title <p>Title: 获取对象类型</p> 
	 * @Description <p>Description: 获取实例类型</p> 
	 * @param instance 实例
	 * @return 对应类型
	 */
	public static Class<?> getUserClass(Object instance) {
		Assert.notNull(instance, "实例不可为null");
		Class clazz = instance.getClass();
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;

	}

	/**
	 * 
	 * @Title <p>Title: checked exception转换为unchecked exception</p> 
	 * @Description <p>Description: 将反射时的checked exception转换为unchecked exception.</p> 
	 * @param e 异常
	 * @return 运行时异常
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException(e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException(((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	/**
	 * 
	 * @Title <p>Title: 判断某个对象是否拥有某个属性</p> 
	 * @Description <p>Description: 判断某个对象是否拥有某个属性</p> 
	 * @param obj 对象
	 * @param fieldName 属性
	 * @return 有属性返回true 无属性返回false
	 */
	public static boolean hasField(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);
		return field == null ? false : true;
	}
}