package com.jess.common.config.commonDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/8/29 21:31
 * @Description:当前数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);
    @Override
    protected Object determineCurrentLookupKey() {
        log.debug("当前数据源为---->{}", DataSourceContextHolder.getDB());
        return DataSourceContextHolder.getDB();
    }
}
