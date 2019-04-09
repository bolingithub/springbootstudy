package com.example.springbootstudy.services.config;

/**
 * 用户登陆认证类型
 */
public enum UserAuthType {
    PHONE("phone"), WEI_BO("weibo"), WEI_XIN("weixin"), QQ("qq"), TOKEN("token");

    private String authType;

    UserAuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthType() {
        return authType;
    }
}
