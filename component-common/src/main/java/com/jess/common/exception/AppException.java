package com.jess.common.exception;


/**
 * 
 * <p>ClassName: AppException</p>
 * <p>Description: 通用程序运行时异常</p>
 * <p>Author: liuyunlong</p>
 * <p>Date: 2017年11月7日</p>
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 930261378317889770L;
    
    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
