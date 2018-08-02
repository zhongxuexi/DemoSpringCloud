/**
* LogUtil.java
* Created at 2017年11月21日
* Created by 胡锐锋
* Copyright (C) 2017 SAIC VOLKSWAGEN, All rights reserved.
*/
package com.jess.commons.api.util;

import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import com.jess.commons.constants.Const;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.jess.commons.constants.Const.*;

/**
 * 
 * @Title <p>ClassName: LogUtil</p>
 * @Description <p>Description: 日志帮助类</p>
 * @Author <p>Author: 胡锐锋</p>
 * @Date <p>Date: 2017年12月19日</p>
 */
public class LogUtil {
	public static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

	private LogUtil() {
		super();
	}

	/**
	 * 
	 * @Title <p>Title: 懒加载输出日志</p> 
	 * @Description <p>Description: 1.懒加载日志代码，解决关闭debug log(getInfo(foo))中getInfo(foo)依然调用的问题。2.调用实例：log(()->getInfo(foo)); 或者log(()->"字符串");</p> 
	 * @param <p>supplier 函数，比如：()->"这是返回结果"</p>
	 */
	public static void log(Supplier<String> supplier) {
		String userInfo = getUserInfoFromRequestStr();
		if (logger.isDebugEnabled()) {
			logger.debug("{} debug: {}", userInfo, supplier.get());
		} else if (logger.isErrorEnabled()) {
			logger.error("{} error: {}", userInfo, supplier.get());
		} else if (logger.isInfoEnabled()) {
			logger.info("{} info: {}", userInfo, supplier.get());
		} else if (logger.isTraceEnabled()) {
			logger.trace("{} trace: {}", userInfo, supplier.get());
		} else if (logger.isWarnEnabled()) {
			logger.warn("{} warn: {}", userInfo, supplier.get());
		}
	}

	/**
	 * 
	 * @Title <p>Title: (单元测试使用)输出日志</p> 
	 * @Description <p>Description: 单元测试使用</p> 
	 * @param <p>supplier 函数，比如：()->"这是返回结果"</p> 
	 */
	public static void test(Supplier<String> supplier) {
		if (logger.isDebugEnabled()) {
			logger.debug("======>>> debug: {}", supplier.get());
		} else if (logger.isErrorEnabled()) {
			logger.error("======>>> error: {}", supplier.get());
		} else if (logger.isInfoEnabled()) {
			logger.info("======>>> info: {}", supplier.get());
		} else if (logger.isTraceEnabled()) {
			logger.trace("======>>> trace: {}", supplier.get());
		} else if (logger.isWarnEnabled()) {
			logger.warn("======>>> warn: {}", supplier.get());
		}
	}
	
	/**
	 * 
	 * @Title <p>Title: 从请求头里面获取用户信息</p> 
	 * @Description <p>Description: 从请求头里面获取用户信息</p> 
	 * @return 用户信息（json格式）
	 */
	public static String getUserInfoFromRequestStr() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (servletRequestAttributes == null) {
			return "";
		}
		HttpServletRequest request = servletRequestAttributes.getRequest();
		String hostIp = "";
		String name = "";
		String id = "";
		String accessToken = "";
		String userInfo = "";
		if (request != null) {
			hostIp = StringUtils.defaultIfBlank(request.getHeader(USERHOST), "");
			name = StringUtils.defaultIfBlank(request.getHeader(USERNAME), "");
			id = StringUtils.defaultIfBlank(request.getHeader(USERID), "");
			accessToken = StringUtils.defaultIfBlank(request.getHeader(ACCESS_TOKEN), "");
			if (accessToken != null && !accessToken.isEmpty() && !accessToken.equals("null")) {
				userInfo = StringUtils.join(Const.LOG_FLAG, " userInfo:{", USERHOST, ":", hostIp, ",", USERNAME, ":",
						name, ",", USERID, ":", id, ",", ACCESS_TOKEN, ":", accessToken, "}");
			}
		}
		return userInfo;
	}

	/**
	 * 
	 * <p>Title: 输出栈</p> 
	 * <p>Description: </p> 
	 * @param <p>supplier 函数，比如：()->"这是返回结果"</p>
	 */
	/**
	 * 
	 * @Title <p>Title: 输出trace级别的日志</p> 
	 * @Description <p>Description: 比debug级别更细</p> 
	 * @param <p>supplier 函数，比如：()->"这是返回结果"</p>
	 */
	public static void trace(Supplier<String> supplier) {
		if (logger.isTraceEnabled()) {
			String userInfo = getUserInfoFromRequestStr();
			logger.trace("{} trace: {}", userInfo, supplier.get());
		}
	}

	/**
	 * 
	 * @Title <p>Title: 输出警告</p> 
	 * @Description <p>Description: 指明潜在有害情况</p> 
	 * @param <p>supplier 函数，比如：()->"这是返回结果"</p>
	 */
	public static void warn(Supplier<String> supplier) {
		if (logger.isWarnEnabled()) {
			String userInfo = getUserInfoFromRequestStr();
			logger.warn("{} warn: {}", userInfo, supplier.get());
		}
	}

	/**
	 * 
	 * @Title <p>Title: 普通输出</p> 
	 * @Description <p>Description: 粗粒度输出描述信息</p> 
	 * @param <p>supplier 函数，比如：()->"这是返回结果"</p>
	 */
	public static void info(Supplier<String> supplier) {
		if (logger.isInfoEnabled()) {
			String userInfo = getUserInfoFromRequestStr();
			logger.info("{} info: {}", userInfo, supplier.get());
		}
	}

	/**
	 * 
	 * @Title <p>Title: 输出错误</p> 
	 * @Description <p>Description: 指明错误事件</p> 
	 * @param <p>supplier 函数，比如：()->"这是返回结果"</p>
	 */
	public static void error(Supplier<String> supplier) {
		if (logger.isErrorEnabled()) {
			String userInfo = getUserInfoFromRequestStr();
			logger.error("{} error: {}", userInfo, supplier.get());
		}
	}

	/**
	 * 
	 * @Title <p>Title: 调试输出</p> 
	 * @Description <p>Description: 指明细致的事件信息</p> 
	 * @param <p>supplier 函数，比如：()->"这是返回结果"</p>
	 */
	public static void debug(Supplier<String> supplier) {
		if (logger.isDebugEnabled()) {
			String userInfo = getUserInfoFromRequestStr();
			logger.debug("{} debug: {}", userInfo, supplier.get());
		}
	}
}
