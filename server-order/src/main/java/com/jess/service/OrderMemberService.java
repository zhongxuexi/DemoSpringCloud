package com.jess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by zhongxuexi on 2018/6/5.
 */
@Service
public class OrderMemberService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Object> getOrderMembers(){

        return restTemplate.getForObject("http://server-member/getAll",List.class);
    }
}

