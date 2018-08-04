/**
* Exception4CallUtil.java
* Created at 2018年3月2日
* Created by 胡锐锋
* Copyright (C) 2017 SAIC VOLKSWAGEN, All rights reserved.
*/
package com.jess.commons.api.util;

import com.jess.commons.util.ResultUtil;
import com.jess.commons.web.SystemResponse;

/**
 * @Title <p>ClassName: Exception4CallUtil</p>
 * @Description <p>Description: 远程调用异常提醒帮助类</p>
 * @Author <p>Author: 胡锐锋</p>
 * @Date <p>Date: 2018年3月2日</p>
 */
public class Exception4CallUtil {

	private Exception4CallUtil() {
		super();
	}

	public static SystemResponse responseTcloudDevicesConnector() {
		return ResultUtil.fail("sys_common_1024", "T盒（接入层）服务异常");
	}

	public static SystemResponse responseTcloudMds() {
		return ResultUtil.fail("sys_common_1025", "主数据服务异常");
	}

	public static SystemResponse responseIDP() {
		return ResultUtil.fail("sys_common_1026", "账户系统（IDP）服务异常");
	}

	/**
	 * 
	 * @Title <p>Title: 短信服务异常提醒</p> 
	 * @Description <p>Description: </p> 
	 * @return
	 */
	public static SystemResponse responseTcloudMsg() {
		return ResultUtil.fail("sys_common_1027", "短信服务异常");
	}

	/** 
	 * @Title <p>Title: 维保服务</p> 
	 * @Description <p>Description: </p> 
	 * @return 
	 */
	public static SystemResponse responsetTcloudTspMaintenance() {
		return ResultUtil.fail("sys_common_1028", "维保服务异常");
	}
	public static SystemResponse responsetTcloudTspNavigation() {
		return ResultUtil.fail("sys_common_1029", "导航服务异常");
	}
	public static SystemResponse responsetTcloudTspCall() {
		return ResultUtil.fail("sys_common_1030", "呼叫服务异常");
	}
	public static SystemResponse responsetTcloudTspFlow() {
		return ResultUtil.fail("sys_common_1030", "流量服务异常");
	}
	public static SystemResponse responsetTcloudTspPayment() {
		return ResultUtil.fail("sys_common_1031", "支付服务异常");
	}

}
