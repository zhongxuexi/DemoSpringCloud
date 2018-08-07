package com.jess.commons.api.exception;

import com.jess.commons.api.util.Exception4CallUtil;
import com.jess.commons.api.util.LogUtil;
import com.jess.commons.util.ResultUtil;
import com.jess.commons.web.SystemResponse;
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
	private SystemResponse handleFeignException(FeignException e) {
		String msg = e.getMessage();
		SystemResponse rs = checkCall(msg);
		if (rs != null) {
			return rs;
		}
		return ResultUtil.fail("sys_common_1013", "远程调用失败");
	}

	/** 
	 * @Title 检查远程调用，区别调用的是哪个微服务
	 * @Description  
	 * @param msg 
	 */
	public SystemResponse checkCall(String msg) {
		SystemResponse systemResponse = null;

		if (msg.contains("MsgFeign#")) {
			systemResponse = Exception4CallUtil.responseTcloudMsg();
		} else if (msg.contains("DevicesConnectorFeign#")) {
			systemResponse = Exception4CallUtil.responseTcloudDevicesConnector();
		} else if (msg.contains("MdsFeign#")) {
			systemResponse = Exception4CallUtil.responseTcloudMds();
		}
		return systemResponse;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(HystrixRuntimeException.class)
	@ResponseBody
	public SystemResponse handleHystrixRuntimeException(HystrixRuntimeException e) {
		String msg = e.getMessage();
		SystemResponse rs = checkCall(msg);
		if (rs != null) {
			return rs;
		}
		return ResultUtil.NO;
	}

	/**
	 * 处理其他异常
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public SystemResponse handleOtherException(Exception e) {
		LogUtil.logger.error("服务出现异常1111111", e);
		if (e instanceof org.springframework.web.bind.MethodArgumentNotValidException) {
			return null;
		} else if (e instanceof MissingServletRequestParameterException) {
			return ResultUtil.fail("sys_common_1007", e.getMessage().replace("Required ", "必要的")
					.replace(" parameter ", "类型的参数").replace(" is not present", "不能为空"));
		} else if (e instanceof FeignException) {
			return handleFeignException((FeignException) e);
		} else if (e instanceof org.springframework.web.method.annotation.MethodArgumentTypeMismatchException) {
			if (e.getMessage().contains(
					"Failed to convert value of type 'java.lang.String' to required type 'java.util.Date';")) {
				return ResultUtil.fail("sys_common_1007", "日期不正确");
			}
			return ResultUtil.fail("sys_common_1006", "系统异常");
		} else {
			return ResultUtil.fail("sys_common_1006", "系统异常");
		}
	}

}
