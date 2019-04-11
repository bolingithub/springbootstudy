package com.example.springbootstudy.utils;

public class UserIdMaker {
    private static final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    public static String makeUserId() {
        long id = idWorker.nextId();
        return id + "";
    }
}
