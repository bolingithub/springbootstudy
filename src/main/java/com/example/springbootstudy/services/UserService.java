package com.example.springbootstudy.services;

import com.example.springbootstudy.database.entity.UserAuths;
import com.example.springbootstudy.database.repository.UserAuthsRepository;
import com.example.springbootstudy.database.repository.UserInfoRepository;
import com.example.springbootstudy.database.repository.UserLoginHistoryRepository;
import com.example.springbootstudy.services.config.UserAuthType;
import com.example.springbootstudy.services.exception.ServiceException;
import com.example.springbootstudy.services.exception.ServiceExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserLoginHistoryRepository userLoginHistoryRepository;

    public void UserLogin(String phone, String password) throws ServiceException {
        List<UserAuths> userAuthsList = userAuthsRepository.findByIdentityTypeAndIdentifierAndCredential(UserAuthType.PHONE.getAuthType(), phone, password);
        if (userAuthsList.isEmpty()) {
            boolean isExist = userAuthsRepository.existsByIdentityTypeAndIdentifier(UserAuthType.PHONE.getAuthType(), phone);
            if (!isExist) {
                throw new ServiceException(ServiceExceptionCode.USER_NO_REGISTER, "当前手机号尚未注册，请先注册");
            } else {
                throw new ServiceException(ServiceExceptionCode.USER_PASSWORD_ERROR, "密码错误，请重新输入");
            }
        }
        if (userAuthsList.size() > 1) {
            throw new ServiceException(ServiceExceptionCode.DEFAULT_ERROR, "系统错误，存在多个认证信息");
        }
    }
}
