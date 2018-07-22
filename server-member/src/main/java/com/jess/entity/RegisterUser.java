package com.jess.entity;

/**
 * Created by zhongxuexi on 2018/7/22.
 */
public class RegisterUser extends User {
    private String emailSecurity;

    public String getEmailSecurity() {
        return emailSecurity;
    }

    public void setEmailSecurity(String emailSecurity) {
        this.emailSecurity = emailSecurity;
    }
}
