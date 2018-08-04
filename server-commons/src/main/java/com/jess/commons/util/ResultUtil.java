package com.jess.commons.util;


import com.jess.commons.web.ResponseMsg;
import com.jess.commons.web.SystemResponse;
import lombok.NoArgsConstructor;

/**
 * 
 * <p>ClassName: Result</p>
 * <p>Description: 响应信息帮助类 </p>
 * <p>Author: 胡锐锋</p>
 * <p>Date: 2017年12月13日</p>
 */
@NoArgsConstructor
public class ResultUtil {
	public static final SystemResponse OK = success();
	public static final SystemResponse NO = fail();

	/**
	 * 
	 * @Title <p>Title: 成功</p> 
	 * @return 成功信息
	 */
	public static SystemResponse success() {
		SystemResponse response = new SystemResponse();
		response.setStatus(true);
		return response;
	}

	/** 
	 * @Title <p>Title: 失败</p> 
	 * @return 失败信息
	 */
	private static SystemResponse fail() {
		SystemResponse response = new SystemResponse();
		response.setStatus(false);
		return response;
	}

	/**
	 * <p>Description: 创建一个“成功”类型的反馈信息。</p>
	 * @param content  处理成功时返回的数据内容
	 * @return 类型为“成功”的反馈信息对象。
	 */

	public static SystemResponse success(Object content) {
		SystemResponse response = new SystemResponse();
		response.setStatus(true);
		response.setData(content);
		return response;
	}

	/**
	 * <p>Description: 创建一个“失败”类型的反馈信息。</p>
	 * @param code   异常编码
	 * @param message 异常消息
	 * @return 类型为“失败”的反馈信息对象。
	 */
	public static SystemResponse fail(String code, String message) {
		SystemResponse response = new SystemResponse();
		response.setStatus(false);
		response.setResponseMsg(new ResponseMsg(code, message));
		return response;
	}

}
