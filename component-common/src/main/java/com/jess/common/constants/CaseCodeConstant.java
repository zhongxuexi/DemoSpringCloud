package com.jess.common.constants;

/**
 * 规则编码的前缀常量
 * <p>ClassName: CaseCodeConstant</p>
 * <p>Description: 常量命名规则 业务实体名称_CASE_CODE</p>
 * <p>Author: liuyunlong</p>
 * <p>Date: 2017年11月2日</p>
 */
public class CaseCodeConstant {

	/**
	 * 公共错误码前缀
	 */
	public static final String COMMON_CASE_CODE = "sys_common_";
	
	/**
	 * 网关规则编码前缀
	 */
	public static final String GATEWAY_CASE_CODE = "gateway";
	public static final int NOT_HAVE_ACCESS_TOKEN = 102;
	public static final int NOT_HAVE_IP = 103;

	
	/**
	 * 账号规则编码前缀
	 */
	public static final String ACCOUNT_CASE_CODE = "account";
	public static final String CODE_CASE_CODE = "202";
	
	/**
	 * 设备接入层模块错误码前缀
	 */
	public static final String DEVICE_CONNECTOR_CASE_CODE="device_connector_";
	/**
	 * 旅程统计错误码前缀
	 */
	public static final String DRIVING_BEHAVIOR_CASE_CODE="driving_behavior_";

	/**
	 * 登录鉴权规则编码前缀
	 */
	public static final String MDS_CASE_CODE = "mds_";
	public static final int ACCESSTOKEN_HAS_EXPIRED = 302;
	public static final String NOT_HAVE_USER = "303";
	public static final String SAVE_ACCESS_TOKEN_FAIL = "304";
	public static final String SAVE_REFRESH_TOKEN_FAIL = "305";
	public static final String REFRESH_HAS_EXPIRED = "306";
	public static final int UNAUTHORIZED_ACCESS = 307;
	public static final String NOT_EXIST_ACCESS_TOKEN_IN_CACHE = "308";
	public static final int INVALID_ACCESSTOKEN = 309;
	public static final int USER_DOES_NOT_EXIST = 310;
	public static final int ENABLED_PATH_OR_UNAUTHORIZED_ACCESS = 311;
	public static final String SAVE_TOKEN_FAIL = "312";
	public static final int URI_IS_OVERDUE = 313;

	public static final String VP_CASE_CODE = "vp_";

	public static final int SUSER_WITHOUT_DEFAULT_VEHICLE = 314;
	private CaseCodeConstant() {
	}

}
