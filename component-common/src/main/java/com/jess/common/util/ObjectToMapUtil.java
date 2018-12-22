package com.jess.common.util;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jess.common.exception.FieldException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by zhongxuexi on 2018/8/11.
 */
public class ObjectToMapUtil<T> {
    private ObjectToMapUtil() {
    }

    public static ObjectToMapUtil getInstance() {
        return new ObjectToMapUtil();
    }

    /**
     * 拆分属性字符串，获得属性字符串数组
     *
     * @param field
     * @param t
     * @return
     * @throws Exception
     */
    public String[] getFields(String field, T t) throws Exception {
        if (org.springframework.util.ObjectUtils.isEmpty(t)) {
            throw new Exception("对象不能为空!");
        }
        if (StringUtils.isNoneBlank(field)) {
            //检查field合法性规则
            if (checkIsValid(field, t)) {
                return field.split(",");
            } else {
                throw new FieldException("field与数据库字段不一致，请输入正确的field值!");
            }
        } else {
            return ReflectionUtils.getAllFields(t);
        }
    }

    /**
     * 通过属性数组，对象，获得Map
     *
     * @param fields
     * @param t
     * @return
     */
    public Map<String, Object> getResultMap(String[] fields, T t) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        if (fields.length > 0) {
            for (String item : fields) {
                Object fieldValue = ReflectionUtils.getFieldValue(t, item);  //属性名获取属性值
                map.put(item, fieldValue);
            }
        }
        return map;
    }

    /**
     * 通过传入的Map，如：map.put("name,age",new User())
     * 返回固定属性的Map
     * 如返回格式:{"name":"nameValue","age":"ageValue"}
     *
     * @param map
     * @return
     */
    public static Map<String, Object> getCombinationMap(Map<String, Object> map) {
        Map<String, Object> combinationMap = Maps.newLinkedHashMap();
        if(ObjectUtils.isEmpty(map)){
            return combinationMap;
        }
        map.keySet().forEach(key -> {
            Object obj = map.get(key);
            if (ObjectUtils.isEmpty(obj)) {
                return;
            }
            try {
                String[] fields = getInstance().getFields(key, obj);    //对象的属性数组
                Map<String, Object> resultMap = getInstance().getResultMap(fields, obj);
                Set<String> keySet = resultMap.keySet();//当前对象keySet
                Set<String> combinationKeySet = combinationMap.keySet();//组合map已存在的keySet

                Map<String, Object> newResultMap = Maps.newLinkedHashMap();//重构的map
                newResultMap.putAll(resultMap);
                int n = 0;
                for (String item : keySet) {
                    if (combinationKeySet.contains(item)) {
                        n++;
                        String str = obj.getClass().getName();
                        String newStr = str.substring(str.lastIndexOf(".") + 1).toLowerCase();
                        String newKey = newStr + "_" + item;
                        newResultMap.put(newKey, newResultMap.remove(item));
                    }
                }
                if (n == 0) {
                    combinationMap.putAll(resultMap);
                } else {
                    combinationMap.putAll(newResultMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.error(() -> e.getMessage());
            }
        });
        return combinationMap;
    }

    /**
     * 通过传入的对象属性字符串,如："name,age"，对象List集合
     * 返回固定属性的Map集合
     * 如返回格式:[{"name1":"nameValue","age1":"ageValue"},{"name2":"nameValue","age2":"ageValue"}]]
     *
     * @param fields
     * @param objList
     * @return
     */
    public List<Map<String, Object>> getCombinationMapList(String fields, List<T> objList) throws Exception {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        if (!objList.isEmpty()) {
            for (T t : objList) {
                Map<String, Object> innerMap = Maps.newLinkedHashMap();
                innerMap.put(fields, t);
                resultList.add(getCombinationMap(innerMap));
            }
        }
        return resultList;
    }

    /**
     * 判断传入的field是否合法
     *
     * @param field
     * @param obj
     * @return boolean
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public static boolean checkIsValid(String field, final Object obj) throws ClassNotFoundException, IllegalAccessException {
        String pattern = "(?i)^(?!,)[a-zA-Z0-9,]+(?<!,)$"; //开头和结尾不能是分隔符,
        boolean isMatch = Pattern.matches(pattern, field);
        if (!isMatch) {
            throw new FieldException("field字段之间必须以英文,分割且首末不能有分割符!");
        }
        String[] fields = field.split(",");
        String[] allFields = ReflectionUtils.getAllFields(obj);
        if (!Arrays.asList(allFields).containsAll(Arrays.asList(fields))) {
            return false;
        }
        return true;
    }
}
