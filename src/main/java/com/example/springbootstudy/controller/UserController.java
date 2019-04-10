package com.example.springbootstudy.controller;

import com.example.springbootstudy.HelloSpringBootController;
import com.example.springbootstudy.controller.dto.DTOFactory;
import com.example.springbootstudy.controller.dto.UserInfoDTO;
import com.example.springbootstudy.database.entity.UserInfo;
import com.example.springbootstudy.services.UserService;
import com.example.springbootstudy.services.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(HelloSpringBootController.class);

    @Autowired
    UserService userService;

    @GetMapping("userLogin")
    public UserInfoDTO login(@RequestParam String phone, @RequestParam String password, String loginIp) throws ServiceException {
        UserInfo userInfo = userService.userLoginByPhone(phone, password, loginIp);
        return new DTOFactory().userInfo2DTO(userInfo);
    }
}
