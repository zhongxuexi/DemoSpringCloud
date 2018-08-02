package com.jess.commons.api.service;

import org.springframework.stereotype.Service;

/**
 * Created by zhongxuexi on 2018/8/2.
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public String findString() {
        return "this a test service";
    }
}
