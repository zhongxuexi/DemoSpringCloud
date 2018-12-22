package com.jess.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/12 23:12
 * @Description: http工具类
 */
public class HttpUtils {

    public static Map<String, Object> getRequestMap(HttpServletRequest request) throws IOException {
        Map<String, String[]> requestMsg = request.getParameterMap();   //parameterMap
        //body
        InputStream io = request.getInputStream();
        String body = IOUtils.toString(io);
        System.out.println("HttpUtils.getRequestMap body-->" + body);

        Map<String, Object> objectMap = Maps.newLinkedHashMap();
        if (StringUtils.isNoneBlank(body)) {
            objectMap = JSONUtil.toMap(body);
        }

        //parameters
        Map<String, Object> parameterMap = Maps.newLinkedHashMap();
        for (String key : requestMsg.keySet()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < requestMsg.get(key).length; i++) {
                //所有请求参数值
                sb.append(requestMsg.get(key)[i].toString());
            }
            parameterMap.put(key, sb.toString());
        }
        objectMap.putAll(parameterMap);
        return objectMap;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }
}
