/**
* ExceptionUtil.java
* Created at 2017年12月6日
* Created by 胡锐锋
* Copyright (C) 2017 SAIC VOLKSWAGEN, All rights reserved.
*/
package com.jess.commons.api.util;

import com.jess.commons.constants.Const;
import com.jess.commons.exception.AppException;

/**
 * <p>ClassName: ExceptionUtil</p>
 * <p>Description: 异常处理工具类</p>
 * <p>Author: 胡锐锋</p>
 * <p>Date: 2017年12月6日</p>
 */
public class ExceptionUtil {

	private ExceptionUtil() {
		super();
	}

	/**
	 * 
	 * @Title <p>Title:  抛异常、记录异常日志</p> 
	 * @Description <p>Description:  抛异常和记录异常日志</p> 
	 * @param e 异常
	 */
	public static void throwAndLogErr(Exception e) {
		LogUtil.logger.error("{} {}", Const.LOG_FLAG, e);
		throw new AppException(e.getMessage());
	}
}
