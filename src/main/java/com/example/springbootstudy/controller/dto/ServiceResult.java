package com.example.springbootstudy.controller.dto;

import lombok.Data;

@Data
public class ServiceResult<T> {
    public static final String SUCCESS = "success";

    private int code;
    private String message;
    private T data;

    public ServiceResult() {
    }

    public ServiceResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
