package com.jess.common.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/12 23:12
 * @Description: 工具类发送post到/refresh地址
 */
public class HttpUtils {
    public static void refresh() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8110/bus/refresh");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.connect();//链接
            InputStream in = connection.getInputStream();//等待响应
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
