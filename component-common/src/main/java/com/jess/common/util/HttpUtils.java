package com.jess.common.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/12 23:12
 * @Description: http工具类
 */
public class HttpUtils {
    public static void sendHttp(String serverURL,String method) throws Exception {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(serverURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(method);
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
