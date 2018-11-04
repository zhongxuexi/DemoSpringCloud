package com.jess.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jess.common.exception.FieldException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by zhongxuexi on 2018/8/11.
 */
public class ObjectToMapUtil {
    /**
     * 拆分属性字符串，获得字符串数组
     *
     * @param field
     * @return
     */
    public static String[] getFields(String field, Object obj) throws Exception {
        if (StringUtils.isNoneBlank(field)) {
            //检查field合法性规则
            if (checkIsValid(field, obj)) {
                return field.split(",");
            } else {
                throw new FieldException("field与数据库字段不一致，请输入正确的field值!");
            }
        } else {
            return ReflectionUtils.getAllFields(obj);
        }
    }

    /**
     * 通过属性数组，结果集合，获得Map集合
     *
     * @param fields
     * @param resultList
     * @return
     */
    public static List<Map<String, Object>> getResultListMap(String[] fields, List<?> resultList) throws IllegalAccessException, ClassNotFoundException {
        List<Map<String, Object>> listMap = Lists.newArrayList();
        for (Object obj : resultList) {
            Map<String, Object> map = Maps.newLinkedHashMap();
            for (String item : fields) {
                if (null != obj) {
                    Object fieldNameValue = ReflectionUtils.getFieldValue(obj, item);  //属性名获取属性值
                    map.put(item, fieldNameValue);
                } else {
                    map.put(item, null);
                }
            }
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 通过属性数组，结果集合，获得Map
     *
     * @param fields
     * @param obj
     * @return
     */
    public static Map<String, Object> getResultMap(String[] fields, Object obj) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        for (String item : fields) {
            //if (null != obj) {
                Object fieldNameValue = ReflectionUtils.getFieldValue(obj, item);  //属性名获取属性值
                map.put(item, fieldNameValue);
            //} else {
               // map.put(item, null);
            //}
        }


        return map;
    }

    public static Map<String, Object> getCombinationMap(Map<String, Object> map) {
        Map<String, Object> combinationMap = Maps.newLinkedHashMap();
        map.keySet().forEach(key -> {
            Object obj = map.get(key);
            try {
                String[] fields = getFields(key, obj);//对象的属性数组
                Map<String, Object> resultMap = getResultMap(fields, obj);
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
