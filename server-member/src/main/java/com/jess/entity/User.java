package com.jess.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_user")
public class User {
    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 登陆名
     */
    private String username;

    private String path;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Byte age;

    /**
     * 学历
     */
    private String education;

    /**
     * 籍贯
     */
    private String nativeplace;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 住址
     */
    private String address;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 0正常，1不正常
     */
    @Column(name = "delete_state")
    private String deleteState;

    public User(Long id, String realname, String username, String path, String password, String sex, Byte age, String education, String nativeplace, String phone, String email, String address, Date createTime, Date updateTime, String deleteState) {
        this.id = id;
        this.realname = realname;
        this.username = username;
        this.path = path;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.education = education;
        this.nativeplace = nativeplace;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleteState = deleteState;
    }

    public User() {
        super();
    }

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取真实姓名
     *
     * @return realname - 真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 设置真实姓名
     *
     * @param realname 真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    /**
     * 获取登陆名
     *
     * @return username - 登陆名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置登陆名
     *
     * @param username 登陆名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * 获取登陆密码
     *
     * @return password - 登陆密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登陆密码
     *
     * @param password 登陆密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Byte getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Byte age) {
        this.age = age;
    }

    /**
     * 获取学历
     *
     * @return education - 学历
     */
    public String getEducation() {
        return education;
    }

    /**
     * 设置学历
     *
     * @param education 学历
     */
    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    /**
     * 获取籍贯
     *
     * @return nativeplace - 籍贯
     */
    public String getNativeplace() {
        return nativeplace;
    }

    /**
     * 设置籍贯
     *
     * @param nativeplace 籍贯
     */
    public void setNativeplace(String nativeplace) {
        this.nativeplace = nativeplace == null ? null : nativeplace.trim();
    }

    /**
     * 获取手机号码
     *
     * @return phone - 手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号码
     *
     * @param phone 手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取住址
     *
     * @return address - 住址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置住址
     *
     * @param address 住址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取0正常，1不正常
     *
     * @return delete_state - 0正常，1不正常
     */
    public String getDeleteState() {
        return deleteState;
    }

    /**
     * 设置0正常，1不正常
     *
     * @param deleteState 0正常，1不正常
     */
    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState == null ? null : deleteState.trim();
    }
}