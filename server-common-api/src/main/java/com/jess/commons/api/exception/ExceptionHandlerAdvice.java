package com.jess.commons.api.exception;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.svw.tbox.tcloud.commons.api.Rule.CommonRule;
import com.svw.tbox.tcloud.commons.api.util.Exception4CallUtil;
import com.svw.tbox.tcloud.commons.api.util.SysErrorCodeMappingUtil;
import com.svw.tbox.tcloud.commons.api.vo.IDPVo;
import com.svw.tbox.tcloud.commons.constants.CmnErrConst;
import com.svw.tbox.tcloud.commons.exception.DynamicRuleException;
import com.svw.tbox.tcloud.commons.exception.RuleException;
import com.svw.tbox.tcloud.commons.util.JSONUtil;
import com.svw.tbox.tcloud.commons.util.LogUtil;
import com.svw.tbox.tcloud.commons.util.ResultUtil;
import com.svw.tbox.tcloud.commons.web.SystemResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.Set;

/**
 * <p>ClassName: ExceptionHandlerAdvice</p>
 * <p>Description: 异常扫描处理</p>
 * <p>Author: 胡锐锋</p>
 * <p>Date: 2017年11月22日</p>
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
	private static final String CLIENT_EXCEPTION = "com.netflix.client.ClientException: Load balancer does not have available server for client: "; // 短信服务异常

	private ExceptionHandlerAdvice() {
	}

	/**
	 * 业务规则异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(RuleException.class)
	@ResponseBody
	public SystemResponse handleRuleException(RuleException e) {
		return ResultUtil.fail(e.getRule().code(), e.getRule().brief());
	}

	/**
	 * 
	 * <p>Title: 处理参数校验不通过异常</p> 
	 * <p>Description: </p>
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseBody
	public SystemResponse handleParamViolationException(ConstraintViolationException e) {

		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		String msg = violations.iterator().next().getMessage();
		String errCode = null;

		StringBuilder sb = new StringBuilder();

		if (msg.contains("#")) {
			String[] split = msg.split("#");
			sb.append(split[1]).append(SysErrorCodeMappingUtil.fail(split[0]).getResponseMsg().getErrorMsg());
			errCode = split[0];
		} else {
			return SysErrorCodeMappingUtil.fail(msg);
		}

		return ResultUtil.fail(errCode, sb.toString());
	}

	/**
	 * 
	 * <p>Title: 处理类型转换异常</p> 
	 * <p>Description: </p>
	 * @return
	 */
	@SuppressWarnings("unused")
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
	@ResponseBody
	public SystemResponse handleTypeMismatchException(MethodArgumentTypeMismatchException e) {

		String errMsg = "类型转换错误: 输入参数:" + e.getValue() + ",需要参数类型为:" + e.getMessage().split("'")[3];
		;

		return ResultUtil.fail(CmnErrConst.TYPE_CONVERT_ERROR, errMsg);
	}

	/**
	 * 
	 * @Title <p>Title: 动态业务异常处理</p> 
	 * @param e 
	 * @return
	 */
	@ExceptionHandler(DynamicRuleException.class)
	@ResponseBody
	public SystemResponse handleDynamicRuleException(DynamicRuleException e) {
		String msg = e.getMessage();
		String[] codeMsg = msg.split("-");
		return ResultUtil.fail(codeMsg[0], codeMsg[1]);
	}

	private SystemResponse handleMethodArgumentNotValidException(Exception e) {
		MethodArgumentNotValidException me = (MethodArgumentNotValidException) e;
		Optional<String> ome = me.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage())
				.findFirst();

		String codeMsg = ome.isPresent() ? ome.get() : "";
		if (codeMsg.contains("#")) {
			String[] cm = codeMsg.split("#");
			try {
				SystemResponse systemResponse = ResultUtil.fail(cm[0],
						SysErrorCodeMappingUtil.fail(cm[0]).getResponseMsg().getErrorMsg() + "#" + cm[1]);
				if (null != systemResponse) {
					return systemResponse;
				}
			} catch (Exception err) {
				LogUtil.logger.error("错误码翻译错误", err);
			}
			return ResultUtil.fail(cm[0], cm[1]);
		} else {
			try {
				SystemResponse systemResponse = SysErrorCodeMappingUtil.fail(codeMsg);
				if (null != systemResponse) {
					return systemResponse;
				}
			} catch (Exception err) {
				LogUtil.logger.error("错误码翻译错误", err);
			}
			return ResultUtil.fail("sys_common_1007", codeMsg);
		}
	}

	/** 
	 * @Title <p>Title: 区分对应远程调用的微服务</p> 
	 * @Description <p>Description: </p> 
	 * @param e
	 * @param callResponse
	 * @return 
	 */
	private SystemResponse switchCallException(Exception e) {
		SystemResponse callResponse = null;
		String msg = e.getMessage();
		if(msg==null) {
			return callResponse;
		}
		if (msg.contains(CLIENT_EXCEPTION + "tcloud-msg")) {
			callResponse = Exception4CallUtil.responseTcloudMsg();
		} else if (msg.contains(CLIENT_EXCEPTION + "tcloud-devices-connector")) {
			callResponse = Exception4CallUtil.responseTcloudDevicesConnector();
		} else if (msg.contains(CLIENT_EXCEPTION + "tcloud-mds")) {
			callResponse = Exception4CallUtil.responseTcloudMds();
		} else if (msg.contains(CLIENT_EXCEPTION + "auth")) {
			callResponse = Exception4CallUtil.responseIDP();
		} else if (msg.contains(CLIENT_EXCEPTION + "tcloud-tsp-maintenance")) {
			callResponse = Exception4CallUtil.responsetTcloudTspMaintenance();
		} else if (msg.contains(CLIENT_EXCEPTION + "tcloud-tsp-navigation")) {
			callResponse = Exception4CallUtil.responsetTcloudTspNavigation();
		} else if (msg.contains(CLIENT_EXCEPTION + "tcloud-tsp-call")) {
			callResponse = Exception4CallUtil.responsetTcloudTspCall();
		} else if (msg.contains(CLIENT_EXCEPTION + "tcloud-tsp-flow")) {
			callResponse = Exception4CallUtil.responsetTcloudTspFlow();
		} else if (msg.contains(CLIENT_EXCEPTION + "tcloud-tsp-payment")) {
			callResponse = Exception4CallUtil.responsetTcloudTspPayment();
		}
		return callResponse;
	}

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
		IDPVo<?> idpVo = null;
		if (msg.contains("MsgFeign#")) {
			systemResponse = Exception4CallUtil.responseTcloudMsg();
		} else if (msg.contains("DevicesConnectorFeign#")) {
			systemResponse = Exception4CallUtil.responseTcloudDevicesConnector();
		} else if (msg.contains("MdsFeign#")) {
			systemResponse = Exception4CallUtil.responseTcloudMds();
		} else if (msg.contains("IdpFeign#")) {
			String[] feignExceptions = msg.split("content:");
			if (feignExceptions.length == 2) {
				idpVo = JSONUtil.toBean(feignExceptions[1], IDPVo.class);
				if (idpVo == null) {
					systemResponse = Exception4CallUtil.responseIDP();
				}
				systemResponse = ResultUtil.fail(idpVo.getSubCode(), idpVo.getMsg());
			}
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
		return ResultUtil.fail(CommonRule.FAIL_CALL_MSG);
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
		LogUtil.logger.error("服务出现异常", e);
		if (e instanceof org.springframework.web.bind.MethodArgumentNotValidException) {
			return handleMethodArgumentNotValidException(e);
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
			SystemResponse callResponse = switchCallException(e);
			if (callResponse != null) {
				return callResponse;
			}
			return ResultUtil.fail("sys_common_1006", "系统异常");
		}
	}

}
