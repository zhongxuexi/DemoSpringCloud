package com.jess.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jess.common.exception.FieldException;
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
public class FieldMapUtil<T> {
    /**
     * 拆分属性字符串，获得字符串数组
     *
     * @param field
     * @return
     */
    public String[] getFields(String field, Object obj) throws Exception {
        if (StringUtils.isNoneBlank(field)) {
            //检查field合法性规则
            if(checkIsValid(field,obj)){
                return field.split(",");
            }else{
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
    public List<Map<String, Object>> getResultMap(String[] fields, List<T> resultList) throws IllegalAccessException, ClassNotFoundException {
        List<Map<String, Object>> listMap = Lists.newArrayList();
        for (T t : resultList) {
            Map<String, Object> map = Maps.newLinkedHashMap();
            for (String item : fields) {
                Object fieldNameValue = ReflectionUtils.getFieldValue(t,item);  //属性名获取属性值
                map.put(item, fieldNameValue);
            }
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 通过属性数组，结果集合，获得Map
     * @param fields
     * @param obj
     * @return
     */
    public Map<String, Object> getResultMap(String[] fields, final Object obj){
        Map<String, Object> map = Maps.newLinkedHashMap();
        for (String item : fields) {
            Object fieldNameValue = ReflectionUtils.getFieldValue(obj,item);  //属性名获取属性值
            map.put(item, fieldNameValue);
        }
        return map;
    }

    /**
     * 判断传入的field是否合法
     * @param field
     * @param obj
     * @return boolean
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public boolean checkIsValid(String field,final Object obj) throws ClassNotFoundException, IllegalAccessException {
        String pattern = "(?i)^(?!,)[a-zA-Z0-9,]+(?<!,)$"; //开头和结尾不能是分隔符,
        boolean isMatch = Pattern.matches(pattern, field);
        if (!isMatch) {
            throw new FieldException("field字段之间必须以英文,分割且首末不能有分割符!");
        }
        String[] fields = field.split(",");
        String[] allFields = ReflectionUtils.getAllFields(obj);
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
}
