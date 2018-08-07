package com.jess;

import com.jess.commons.util.EmailUtil;
import org.apache.log4j.Logger;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * Created by zhongxuexi on 2018/6/14.
 */
public class TestMail {
    private static final Logger logger = Logger.getLogger(TestMail.class);

    public static void main(String[] args) {
        try {
            EmailUtil.sendHtmlMail("jess.zhong@aliyun.com","测试","第一份邮件");
            System.out.println("发送成功");
            logger.error("个大概豆腐干郭德纲的郭德纲郭德纲");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
