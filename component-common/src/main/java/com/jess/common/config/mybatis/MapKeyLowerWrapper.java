package com.jess.common.config.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.util.Map;

/**
 * Description: 将Map的key全部转换为小写
 * Author: zhongxuexi
 * Date: 2018/11/30 13:45
 */
public class MapKeyLowerWrapper extends MapWrapper {

    public MapKeyLowerWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        return name == null ? "" : name.toLowerCase();
//        if(useCamelCaseMapping){
//            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,name);
//        }
//        return name;

//        if (useCamelCaseMapping
//                && ((name.charAt(0) >= 'A' && name.charAt(0) <= 'Z')
//                || name.indexOf("_") >= 0)) {
//            return underlineToCamelhump(name);
//        }
//        return name;
    }

    /**
     * 将下划线风格替换为驼峰风格
     *
     * @param inputString
     * @return
     */
//    public String underlineToCamelhump(String inputString) {
//        StringBuilder sb = new StringBuilder();
//
//        boolean nextUpperCase = false;
//        for (int i = 0; i < inputString.length(); i++) {
//            char c = inputString.charAt(i);
//            if (c == '_') {
//                if (sb.length() > 0) {
//                    nextUpperCase = true;
//                }
//            } else {
//                if (nextUpperCase) {
//                    sb.append(Character.toUpperCase(c));
//                    nextUpperCase = false;
//                } else {
//                    sb.append(Character.toLowerCase(c));
//                }
//            }
//        }
//        return sb.toString();
//    }
}
