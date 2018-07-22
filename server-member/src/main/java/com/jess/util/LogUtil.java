package com.jess.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhongxuexi on 2018/7/22.
 */
public class LogUtil {
    // 私有构造
    private LogUtil(){}

    public static Logger getLogger(Class classInstance){
        return LoggerFactory.getLogger(classInstance);
    }
}
