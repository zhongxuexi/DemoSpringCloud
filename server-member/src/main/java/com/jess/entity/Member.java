package com.jess.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhongxuexi on 2018/6/7.
 */
@Data
public class Member {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private Date createTime;
    private Date updateTime;
    private byte status;

    public Member() {
    }

    public Member(String name, String sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }
}
