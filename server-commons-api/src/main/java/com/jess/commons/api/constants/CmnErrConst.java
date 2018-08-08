package com.jess.commons.api.constants;

/**
 * 
 * <p>ClassName: CommonInnerErrorCodeConstant.java</p>
 * <p>Description: 公用内部错误码常量</p>
 * <p>Author: 徐天俊</p>
 * <p>Date: 2017年12月27日</p>
 *
 */
public class CmnErrConst {

	private CmnErrConst() {
		super();
	}
	
	/**
	 * 系统错误
	 */
	public static final String SYSTEM_ERROR = "sys_common_1000";
	
	/**
	 * 响应超时
	 */
	public static final String NO_RESPONSE_TIMEOUT = "sys_common_1001";
	
	/**
	 * 超最大重试次数
	 */
	public static final String OUT_RETRY_TIMES = "sys_common_1002";
	
	/**
	 * 编码错误
	 */
	public static final String ENCODE_ERROR = "sys_common_1003";
	
	/**
	 * 解码错误
	 */
	public static final String DECODE_ERROR = "sys_common_1004";
	
	/**
	 * Token过期
	 */
	public static final String TOKEN_EXPIRED = "sys_common_1005";
	
	/**
	 * 必要信息不能为空
	 */
	public static final String PARAMS_IS_BLANK = "sys_common_1007";
	
	/**
	 * 手机号无效
	 */
	public static final String TEL_NOT_VALIDATE = "sys_common_1011";
	
	/**
	 * 未传入userId
	 */
	public static final String USER_ID_NOT_EXIST = "sys_common_1014";
	
	/**
	 * 时间参数转换错误
	 */
	public static final String DATE_CONVERT_ERROR = "sys_common_1015";
	
	/**
	 * 状态格式不符
	 */
	public static final String STATUS_NOT_VALIDATE = "sys_common_1016";
	
	/**
	 * 经纬度格式错误
	 */
	public static final String LON_AND_LAT_NOT_VALIDATE = "sys_common_1017";
	
	/**
	 * 当前页数必须大于等于1
	 */
	public static final String PAGE_INDEX_NOT_VALIDATE = "sys_common_1018";
	
	/**
	 * 每页显示条数必须大于等于1
	 */
	public static final String PAGE_SIZE_NOT_VALIDATE = "sys_common_1019";
	
	/**
	 * vin码格式不正确
	 */
	public static final String VIN_NOT_VALIDATE = "sys_common_1021";
	
	/**
	 * 必须为数字类型
	 */
	public static final String MUST_BE_NUM = "sys_common_1023";
	
	/**
	 * 数字小于最小值
	 */
	public static final String LOWER_THAN_MIN = "sys_common_1028";
	
	/**
	 * 数字大于最大值
	 */
	public static final String GREATER_THAN_MAX = "sys_common_1029";
	
	/**
	 * 类型转换异常
	 */
	public static final String TYPE_CONVERT_ERROR = "sys_common_1038";
	
}
