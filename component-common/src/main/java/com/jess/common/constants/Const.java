/**
* EntityConstant.java
* Created at 2017年11月14日
* Created by 胡锐锋
* Copyright (C) 2017 SAIC VOLKSWAGEN, All rights reserved.
*/
package com.jess.common.constants;

/**
* <p>ClassName: EntityConstant</p>
* <p>Description: 通用常量</p>
* <p>Author: 胡锐锋</p>
* <p>Date: 2017年11月14日</p>
*/
public class Const {

	/**
	 * 公共参数值：版本、删除标志
	 */
	public static final String VERSION_STR = "1";
	public static final int VERSION_INT = 1;
	public static final short IS_VALID_SHORT = Short.valueOf("1"); // 正常的状态
	public static final short IS_UNVALID_SHORT = Short.valueOf("0"); // 删除的状态
	public static final String OK = "1"; // 有用/是 （通用是状态）
	public static final String NO = "0"; // 无用/不是 （通用否状态）
	public static final String MSG_MS_EX_CODE = "sys_common_1013"; // 短信服务异常

	/**
	 * <p>Field IS_SUCCESS:网关返回成功标识</p>
	 */
	public static final String IS_SUCCESS = "isSuccess";

	private Const() {
		super();
	}

	/**
	 * <p>Field CRTUSER:创建者id</p>
	 */
	public static final String CRTUSER = "createBy";

	/**
	 * <p>Field CRTHOST:创建者IP</p>
	 */
	public static final String CRTHOST = "createHost";

	/**
	 * <p>Field CRTTIME:创建时间</p>
	 */
	public static final String CRTTIME = "createDate";

	/**
	 * <p>Field UPDUSER:修改人id</p>
	 */
	public static final String UPDUSER = "updateBy";

	/**
	 * <p>Field UPDHOST:更新者IP</p>
	 */
	public static final String UPDHOST = "updateHost";

	/**
	 * <p>Field UPDTIME:更新时间</p>
	 */
	public static final String UPDTIME = "updateDate";

	// ------从前台获取的用户信息--bgn--------------
	/**
	 * <p>Field USERHOST:用户主机</p>
	 */
	public static final String USERHOST = "userHost";

	/**
	 * <p>Field USERNAME:用户名</p>
	 */
	public static final String USERNAME = "userName";

	/**
	 * <p>Field USERID:用户号</p>
	 */
	public static final String USERID = "uid";

	/**
	 * 状态 1正常 0删除
	 */
	public static final String IS_VALID = "isValid";

	/**
	 * 版本
	 */
	public static final String VERSION = "version";

	// ------从前台获取的用户信息--end--------------

	/**
	 * <p>Field ENCRYPTHION_KEY:加密算法标识</p>
	 */
	public static final String ENCRYPTHION_KEY = "HmacSHA256";

	/**
	 * <p>访问票剧标识</p>
	 */
	public static final String ACCESS_TOKEN = "accessToken";

	/**
	 * 日志标识
	 */
	public static final String LOG_FLAG = "==>>";

	/***********************错误码工具类常量******************************/
	/**
	 * 存入redis中所有错误码的String结构key
	 */
	public static final String SYS_CODE_ALL = "sys-code-all";

	/**
	 * 存入redis中错误码的 Hash结构key
	 */
	public static final String ERROR_CODE = "error-code";

	/**
	 * 公共错误码接口编号
	 */
	public static final String API_CODE_COMMON = "T_SYS_COMMON";

	/**
	 * 手机错误码接口编号前缀
	 */
	public static final String PREFIX_MP = "MP";

	/**
	 * Tbox错误码接口编号前缀
	 */
	public static final String PREFIX_TBOX = "TBOX";

	/**
	 * 刷新错误码码的消息
	 */
	public static final String MSG_APICODE_REFRESH = "refreshApiCode";
}
