package com.jess.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Description: 配置工具
 * Author: zhongxuexi
 * Date: 2018/12/21 15:12
 */
public class ConfigUtils {

    public static List<String> getZuulYmlConfigNofilter(String url, RestTemplate restTemplate) {
        Map<String, Object> resultMap = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> sourceList = (List<Map<String, Object>>) resultMap.get("propertySources");
        Map<String, Object> sourceMap = (Map<String, Object>) sourceList.get(0).get("source");
        int i = 0;
        List<String> list = Lists.newArrayList();
        while (!ObjectUtils.isEmpty(sourceMap.get("nofilter.sessionid.paths[" + i + "]"))) {
            String path = sourceMap.get("nofilter.sessionid.paths[" + i + "]").toString();
            list.add(path);
            i++;
        }
        return list;
    }
}
