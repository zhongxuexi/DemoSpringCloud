package com.jess.commons.api.exception;

import com.jess.commons.api.util.CodeMsg;
import com.jess.commons.api.util.LogUtil;
import com.jess.commons.api.util.Result;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>ClassName: ExceptionHandlerAdvice</p>
 * <p>Description: 异常扫描处理</p>
 * <p>Author: zhongxuexi</p>
 * <p>Date: 2018年8月2日</p>
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
	private Result handleFeignException(FeignException e) {
		String msg = e.getMessage();
		Result rs = checkCall(msg);
		if (rs != null) {
			return rs;
		}
		return Result.error(CodeMsg.SERVER_EXCEPTION, "远程调用失败");
	}

	/** 
	 * @Title 检查远程调用，区别调用的是哪个微服务
	 * @Description  
	 * @param msg 
	 */
	public Result checkCall(String msg) {
		Result result = null;

		if (msg.contains("MsgFeign#")) {

		} else if (msg.contains("DevicesConnectorFeign#")) {

		} else if (msg.contains("MdsFeign#")) {

		}
		return result;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(HystrixRuntimeException.class)
	@ResponseBody
	public Result handleHystrixRuntimeException(HystrixRuntimeException e) {
		String msg = e.getMessage();
		Result rs = checkCall(msg);
		if (rs != null) {
			return rs;
		}
		return Result.error(CodeMsg.NOT_FIND_DATA);
	}

	/**
	 * 处理其他异常
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result handleOtherException(Exception e) {
		LogUtil.logger.error("服务出现异常", e);
		if (e instanceof org.springframework.web.bind.MethodArgumentNotValidException) {
			return null;
		} else if (e instanceof MissingServletRequestParameterException) {
			return Result.error(CodeMsg.SERVER_EXCEPTION, e.getMessage().replace("Required ", "必要的")
					.replace(" parameter ", "类型的参数").replace(" is not present", "不能为空"));
		} else if (e instanceof FeignException) {
			return handleFeignException((FeignException) e);
		} else if (e instanceof org.springframework.web.method.annotation.MethodArgumentTypeMismatchException) {
			if (e.getMessage().contains(
					"Failed to convert value of type 'java.lang.String' to required type 'java.util.Date';")) {
				return Result.error(CodeMsg.SERVER_EXCEPTION, "日期不正确");
			}
			return Result.error(CodeMsg.SERVER_EXCEPTION, "系统异常");
		} else {
			return Result.error(CodeMsg.SERVER_EXCEPTION, "未知的系统异常");
		}
	}

}
