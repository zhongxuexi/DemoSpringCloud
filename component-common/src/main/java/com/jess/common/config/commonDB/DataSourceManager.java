package com.jess.common.config.commonDB;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/3 23:13
 * @Description:
 */
public class DataSourceManager<T> {
    private T t;

    public DataSourceManager(T t) {
        this.t = t;
    }

    public static <T> T getDataSourceMapper(T t, String dataSourceType){
        DataSourceContextHolder.setDB(dataSourceType);
        return t;
    }
}
