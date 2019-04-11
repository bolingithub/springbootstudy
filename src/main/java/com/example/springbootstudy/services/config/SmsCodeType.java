package com.example.springbootstudy.services.config;

public enum SmsCodeType {

    REGISTER(0), RESET_PWD(1);

    private int type;

    private SmsCodeType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
