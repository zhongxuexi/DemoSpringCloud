package com.jess.commons.exception;


/**
 * 
 * <p>ClassName: DynamicRuleException</p>
 * <p>Description: 不在数据库做统计的大众系统异常</p>
 * <p>Author: 胡锐峰</p>
 * <p>Date: 2017年11月7日</p>
 */
public class DynamicRuleException extends RuntimeException {
	private static final long serialVersionUID = -1232619280101165595L;

	public DynamicRuleException(String code,String message) {
        super(code+"-"+message);
    }
}
