package com.example.springbootstudy.error.exception;

/**
 * 服务异常
 */
public class ServiceException extends Exception {
    private int code;
    private String message;

    public ServiceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
