package com.jess.commons.web;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * <p>ClassName: SystemResponse</p>
 * <p>Description: 响应信息</p>
 * <p>Author: 胡锐锋</p>
 * <p>Date: 2017年12月13日</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="SystemResponse",description="响应信息")
public class SystemResponse<T> implements Serializable {
	private static final long serialVersionUID = 313120539414699039L;
	@ApiModelProperty(value="响应状态",required=true)
	private boolean status;
	
	@ApiModelProperty(value="异常消息")
	private ResponseMsg responseMsg = null;// 异常消息
	
	@ApiModelProperty(value="响应数据")
	private T data;// 响应的数据

}