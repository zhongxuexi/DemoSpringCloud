package com.jess.order.service.impl;

import com.jess.common.config.commonDB.DS;
import com.jess.common.constants.DataSourceConstant;
import com.jess.order.dao.extend.VersionExtendMapper;
import com.jess.order.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/8 18:08
 * @Description:
 */
@Service
public class VersionServiceImpl implements VersionService {
    @Autowired
    private VersionExtendMapper versionExtendMapper;

    @DS(value = DataSourceConstant.DB_MASTER)
    @Override
    public List<Map<String, Object>> find() {
        return versionExtendMapper.find();
    }
}
