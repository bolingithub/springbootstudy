package com.example.springbootstudy.utils;

import java.util.UUID;

public class TokenUtil {

    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

}
