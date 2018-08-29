package com.jess.common.config.multipleDB;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/8/29 21:31
 * @Description:当前数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("当前数据源为"+DataSourceContextHolder.getDB());
        return DataSourceContextHolder.getDB();
    }
}
