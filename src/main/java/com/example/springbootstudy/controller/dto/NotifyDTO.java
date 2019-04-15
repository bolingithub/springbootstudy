package com.example.springbootstudy.controller.dto;

import lombok.Data;

@Data
public class NotifyDTO {
    private long id;
    private String title;
    private String content;
    private java.sql.Timestamp createTime;
}
