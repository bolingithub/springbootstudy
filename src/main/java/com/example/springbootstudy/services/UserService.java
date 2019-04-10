package com.example.springbootstudy.services;

import com.example.springbootstudy.database.entity.SmsCode;
import com.example.springbootstudy.database.entity.UserAuths;
import com.example.springbootstudy.database.entity.UserInfo;
import com.example.springbootstudy.database.entity.UserLoginHistory;
import com.example.springbootstudy.database.repository.SmsCodeRepository;
import com.example.springbootstudy.database.repository.UserAuthsRepository;
import com.example.springbootstudy.database.repository.UserInfoRepository;
import com.example.springbootstudy.database.repository.UserLoginHistoryRepository;
import com.example.springbootstudy.services.config.UserAuthType;
import com.example.springbootstudy.error.exception.ServiceException;
import com.example.springbootstudy.error.exception.ServiceExceptionCode;
import com.example.springbootstudy.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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

    @Autowired
    SmsCodeRepository smsCodeRepository;

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

    /**
     * 保存短信验证码
     *
     * @param phone
     */
    public String saveSmsCode(String phone) {
        String ranCode = RandomUtil.make6IntString();
        SmsCode smsCode = new SmsCode();
        smsCode.setPhone(phone);
        smsCode.setSmsCode(ranCode);
        smsCode.setStatus(0);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        java.sql.Timestamp expiryTime = new Timestamp(calendar.getTime().getTime());
        smsCode.setExpiryTime(expiryTime);
        smsCodeRepository.save(smsCode);
        return ranCode;
    }

    /**
     * 用户通过手机号+验证码注册
     *
     * @param phone
     * @param smsCode
     * @param password
     * @throws ServiceException
     */
//    public void userRegisterByPhone(String phone, String smsCode, String password) throws ServiceException {
//        List<SmsCode> smsCodeList = smsCodeRepository.findByPhoneAndStatus(phone, 0);
//        boolean isCheckSuccess = false;
//        for (SmsCode smsCodeItem : smsCodeList) {
//            Date nowDate = new Date();
//            long compareResult = nowDate.getTime() - smsCodeItem.getCreateTime().getTime();
//            long compareMinute = compareResult / 1000 / 60;
//            logger.debug("短信验证码过期时间：" + compareMinute);
//            if (smsCodeItem.getSmsCode().equals(smsCode)) {
//                // 短信验证码10分钟过期
//                if (compareMinute > 10) {
//                    throw new ServiceException(ServiceExceptionCode.SMS_CODE_EXPIRED, "短信验证码已过期");
//                } else {
//                    isCheckSuccess = true;
//                    break;
//                }
//            }
//        }
//        if (!isCheckSuccess) {
//            throw new ServiceException(ServiceExceptionCode.SMS_CODE_ERROR, "验证码错误，请重新输入");
//        }
//        // 短信验证码通过，将短信验证码改为已使用
//        for (SmsCode smsCodeItem : smsCodeList) {
//            smsCodeItem.setStatus(1);
//        }
//        smsCodeRepository.saveAll(smsCodeList);
////        userInfoRepository.save()
//    }
}
