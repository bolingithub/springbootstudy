package com.example.springbootstudy.services;

import com.example.springbootstudy.database.entity.UserAuths;
import com.example.springbootstudy.database.entity.UserInfo;
import com.example.springbootstudy.database.entity.UserLoginHistory;
import com.example.springbootstudy.database.repository.UserAuthsRepository;
import com.example.springbootstudy.database.repository.UserInfoRepository;
import com.example.springbootstudy.database.repository.UserLoginHistoryRepository;
import com.example.springbootstudy.services.config.UserAuthType;
import com.example.springbootstudy.error.exception.ServiceException;
import com.example.springbootstudy.error.exception.ServiceExceptionCode;
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

    /**
     * 用户通过手机号码登陆
     *
     * @param phone
     * @param password
     * @param loginIp
     * @return
     * @throws ServiceException
     */
    public UserInfo userLoginByPhone(String phone, String password, String loginIp) throws ServiceException {
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
        UserAuths userAuths = userAuthsList.get(0);
        UserInfo userInfo = userInfoRepository.findById(userAuths.getId()).orElse(null);
        if (userInfo == null) {
            throw new ServiceException(ServiceExceptionCode.DEFAULT_ERROR, "系统错误，未找到相关的用户信息");
        }
        // 写入登陆历史记录
        UserLoginHistory userLoginHistory = new UserLoginHistory();
        userLoginHistory.setUserId(userInfo.getId());
        userLoginHistory.setLoginIp(loginIp);
        userLoginHistoryRepository.save(userLoginHistory);
        return userInfo;
    }
}
