package com.jess.common.exception;


/**
 * 
 * <p>ClassName: FieldException</p>
 * <p>Description: field异常</p>
 * <p>Author: zhongxuexi</p>
 * <p>Date: 2018年8月7日</p>
 */
public class FieldException extends RuntimeException {

    private static final long serialVersionUID = 930261378317889770L;

    public FieldException(String message) {
        super(message);
    }

    public FieldException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
