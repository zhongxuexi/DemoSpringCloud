/**
* ResponseMsg.java
* Created at 2017年11月27日
* Created by 胡锐锋
* Copyright (C) 2017 SAIC VOLKSWAGEN, All rights reserved.
*/
package com.jess.commons.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>ClassName: ResponseMsg</p>
 * <p>Description: 响应错误信息</p>
 * <p>Author: 胡锐锋</p>
 * <p>Date: 2017年11月27日</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMsg implements Serializable {
	private static final long serialVersionUID = -6301441651321596047L;
	private String errorCode;
	private String errorMsg;
}
