package com.jess.common.config.multipleDB;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/8/29 21:31
 * @Description:数据源操作类
 */
public class DataSourceContextHolder {
    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "datasource1";
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDB(String dbType) {
        System.out.println("切换到{" + dbType + "}数据源");
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}
