package com.example.springbootstudy.controller;

import com.example.springbootstudy.controller.dto.DTOFactory;
import com.example.springbootstudy.controller.dto.ServiceResult;
import com.example.springbootstudy.controller.dto.UserInfoDTO;
import com.example.springbootstudy.database.entity.UserInfo;
import com.example.springbootstudy.error.exception.ServiceExceptionCode;
import com.example.springbootstudy.services.UserService;
import com.example.springbootstudy.error.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.springbootstudy.controller.dto.ServiceResult.SUCCESS;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @GetMapping("userLogin")
    public ServiceResult<UserInfoDTO> login(@RequestParam String phone, @RequestParam String password, String loginIp) throws ServiceException {
        checkoutPhone(phone);
        UserInfo userInfo = userService.userLoginByPhone(phone, password, loginIp);
        return new ServiceResult<>(0, SUCCESS, DTOFactory.userInfo2DTO(userInfo));
    }

    @GetMapping("sendSmsCode")
    public ServiceResult<String> sendSmsCode(@RequestParam String phone) throws ServiceException {
        checkoutPhone(phone);
        String smsCode = userService.sendRegisterSmsCode(phone);
        return new ServiceResult<>(0, SUCCESS, smsCode);
    }

    @GetMapping("userRegister")
    public ServiceResult<Void> register(@RequestParam String phone, @RequestParam String smsCode, @RequestParam String password) throws ServiceException {
        checkoutPhone(phone);
        userService.userRegisterByPhone(phone, smsCode, password);
        return new ServiceResult<>(0, SUCCESS, null);
    }

    // 判断手机号是否符合要求
    private void checkoutPhone(String phone) throws ServiceException {
        if (phone.length() != 11) {
            throw new ServiceException(ServiceExceptionCode.PARAMS_ERROR, "手机格式不正确，请重新输入");
        }
    }
}
