package com.jess.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * <p>ClassName: JSONUtil</p>
 * <p>Description: JSON字符串和Java对象互相转换类</p>
 * <p>Author: 胡锐锋</p>
 * <p>Date: 2017年11月18日</p>
 */
public class JSONUtil {

	private JSONUtil() {
		super();
	}

	public static String toJson(Object obj) {
		return JSON.toJSONStringWithDateFormat(obj,"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 
	 * @Title <p>Title: 将json转成指定的对象</p> 
	 * @Description <p>Description: 将json转成指定的对象</p> 
	 * @param jsonData json字符串
	 * @param clazz 目标类型
	 * @return 目标对象
	 */
	public static <T> T toBean(String jsonData, Class<T> clazz) {
		return JSONObject.parseObject(jsonData, clazz);
	}
	
	/**
	 * 
	 * <p>Title: 将jsonObj转成指定的对象</p> 
	 * <p>Description: 将jsonObj转成指定的对象</p>
	 * @param jsonData json对象
	 * @param clazz 目标类型
	 * @return 目标对象
	 */
	public static <T> T toBean(Object jsonObj, Class<T> clazz) {
		return JSON.parseObject(JSON.toJSONString(jsonObj), clazz);
	}

	/**
	 * 
	 * @Title <p>Title: String转Map</p> 
	 * @Description <p>Description: 根据传入的类型引入，转成对应的Map</p> 
	 * @param jsonData json字符串
	 * @param tr Map类型引入
	 * @return
	 */
	public static <K, V> Map<K, V> toTypeMap(String jsonData, TypeReference<Map<K, V>> tr) {
		return JSON.parseObject(jsonData, tr);
	}

	/**
	 * 
	 * @Title <p>Title: json转Map<String,Object></p> 
	 * @Description <p>Description: 将json字符串转成Map<String,Object></p> 
	 * @param jsonData json字符串
	 * @return Map对象
	 */
	public static Map<String, Object> toMap(String jsonData) {
		return JSON.parseObject(jsonData, new TypeReference<Map<String, Object>>() {
		});
	}
	
	/**
	 * 
	 * <p>Title: Object转Map<String,Object></p> 
	 * <p>Description: 将object转成Map<String,Object></p>
	 * @param jsonData object
	 * @return Map对象
	 */
	public static Map<String, Object> toMap(Object jsonData){
		JSONObject jsonObject=(JSONObject)JSON.toJSON(jsonData);
		Set<Entry<String,Object>> entrySet=jsonObject.entrySet();
		Map<String,Object> map=new HashMap<>();
		for(Entry<String,Object> entry:entrySet) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	/**
	 * 
	 * @Title <p>Title: json转List</p> 
	 * @Description <p>Description: 将json字符串转成对应类型的集合</p> 
	 * @param jsonData 需要转换的json字符串
	 * @param clazz 目标类型
	 * @return 目标类型的集合
	 */
	public static <T> List<T> toList(String jsonData, Class<T> clazz) {
		return JSON.parseArray(jsonData, clazz);
	}
	
	/**
	 * 
	 * <p>Title: jsonObject转List</p> 
	 * <p>Description: 将Object对象转成对应类型的集合</p>
	 * @param jsonData 需要转换的json对象
	 * @param clazz 目标类型
	 * @return 目标类型的集合
	 */
	public static <T> List<T> toList(Object jsonData, Class<T> clazz){
		return JSON.parseArray(JSON.toJSONString(jsonData), clazz);
	}

}