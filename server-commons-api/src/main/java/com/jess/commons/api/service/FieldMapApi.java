package com.jess.commons.api.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jess.commons.api.exception.FieldException;
import com.jess.commons.util.DateUtil;
import com.jess.commons.util.LogUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by zhongxuexi on 2018/8/11.
 */
@Service
public class FieldMapApi<T> {
    /**
     * 拆分属性字符串，获得字符串数组
     *
     * @param field
     * @return
     */
    public String[] getFields(String field, Class c) throws Exception {
        if (StringUtils.isNoneBlank(field)) {
            //检查field合法性规则
            if(checkIsValid(field,c)){
                return field.split(",");
            }else{
                throw new FieldException("field与数据库字段不一致，请输入正确的field值!");
            }
        } else {
            return getAllFields(c);
        }
    }

    private String[] getAllFields(Class c) throws IllegalAccessException, ClassNotFoundException {
        //获取Class对象
        Field[] fields = c.getDeclaredFields();
        Field.setAccessible(fields, true);
        String[] str = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);      //设置对象的访问权限，保证对private的属性的访问
            str[i] = fields[i].getName();
        }
        return str;
    }

    /**
     * 通过属性数组，结果集合，获得Map集合
     *
     * @param fields
     * @param resultList
     * @return
     */
    public List<Map<String, Object>> getResultMap(String[] fields, List<T> resultList) throws IllegalAccessException, ClassNotFoundException {
        List<Map<String, Object>> listMap = Lists.newArrayList();
        for (T t : resultList) {
            Map<String, Object> map = Maps.newLinkedHashMap();
            for (String item : fields) {
                Object fieldNameValue = getFieldValueByFieldName(item, t);  //属性名获取属性值
                map.put(item, fieldNameValue);
            }
            listMap.add(map);
        }
        return listMap;
    }

    public Map<String, Object> getResultMap(String[] fields, T t){
        Map<String, Object> map = Maps.newLinkedHashMap();
        for (String item : fields) {
            Object fieldNameValue = getFieldValueByFieldName(item, t);  //属性名获取属性值
            map.put(item, fieldNameValue);
        }
        return map;
    }

    /**
     * 判断传入的field是否合法
     * @param field
     * @param c
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public boolean checkIsValid(String field,Class c) throws ClassNotFoundException, IllegalAccessException {
        String pattern = "(?i)^(?!,)[a-zA-Z0-9,]+(?<!,)$"; //开头和结尾不能是分隔符,
        boolean isMatch = Pattern.matches(pattern, field);
        if (!isMatch) {
            throw new FieldException("field字段之间必须以英文,分割且首末不能有分割符!");
        }
        String[] fields = field.split(",");
        String[] allFields = getAllFields(c);
        boolean flag = false;
        for(String outItem:fields){
            for(String innerItem:allFields){
                if(StringUtils.equalsIgnoreCase(outItem,innerItem)){
                    flag=true;
                    break;
                }else {
                    flag = false;
                }
            }
            if(flag == false){
                return false;
            }
        }
        return true;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param t
     * @return
     */
    private Object getFieldValueByFieldName(String fieldName, T t) {
        try {
            Field field = t.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);  //设置对象的访问权限，保证对private的属性的访问
            Object obj = null;
            //时间戳转换
            Type type = field.getGenericType();
            if (type.toString().equals("class java.util.Date")) {
                obj = DateUtil.Date2Str((Date) field.get(t));
            } else {
                obj = field.get(t);
            }
            return obj;
        } catch (Exception e) {
            LogUtil.logger.info(e.getMessage());
            return null;
        }
    }
}
