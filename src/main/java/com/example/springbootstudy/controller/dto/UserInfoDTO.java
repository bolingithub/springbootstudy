package com.example.springbootstudy.controller.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
    private String userId;
    private String phone;
    private String nickname;
    private String personalSign;
    private String sex;
    private String realName;
    private String personalId;
    private String token;
}
