package com.example.springbootstudy.services;

import com.example.springbootstudy.database.entity.*;
import com.example.springbootstudy.database.repository.*;
import com.example.springbootstudy.services.config.SmsCodeType;
import com.example.springbootstudy.services.config.UserAuthType;
import com.example.springbootstudy.error.exception.ServiceException;
import com.example.springbootstudy.error.exception.ServiceExceptionCode;
import com.example.springbootstudy.utils.RandomUtil;
import com.example.springbootstudy.utils.TokenUtil;
import com.example.springbootstudy.utils.UserIdMaker;
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

    @Autowired
    UserFollowRepository userFollowRepository;

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
        List<UserInfo> userInfoList = userInfoRepository.findByUserId(userAuths.getUserId());
        if (userInfoList.size() != 1) {
            logger.error("用户信息错误，可能存在多个用户信息：" + userInfoList.size());
            throw new ServiceException(ServiceExceptionCode.DEFAULT_ERROR, "系统错误，用户信息错误");
        }
        UserInfo userInfo = userInfoList.get(0);
        // 写入登陆历史记录
        UserLoginHistory userLoginHistory = new UserLoginHistory();
        userLoginHistory.setUserId(userInfo.getUserId());
        userLoginHistory.setLoginIp(loginIp);
        userLoginHistoryRepository.save(userLoginHistory);
        return userInfo;
    }

    /**
     * 保存用户token
     *
     * @param userId
     * @return
     * @throws ServiceException
     */
    public String saveUserToken(String userId) throws ServiceException {
        List<UserAuths> userAuthsList = userAuthsRepository.findByIdentityTypeAndIdentifier(UserAuthType.TOKEN.getAuthType(), userId);
        if (userAuthsList.size() > 1) {
            throw new ServiceException(ServiceExceptionCode.DEFAULT_ERROR, "系统错误，存在多个认证信息");
        }
        String token = TokenUtil.getUUID32();
        if (userAuthsList.isEmpty()) {
            UserAuths userAuths = new UserAuths();
            userAuths.setUserId(userId);
            userAuths.setIdentityType(UserAuthType.TOKEN.getAuthType());
            userAuths.setIdentifier(userId);
            userAuths.setCredential(token);
            userAuthsRepository.save(userAuths);
        } else {
            // 已存在更新Token
            UserAuths userAuths = userAuthsList.get(0);
            userAuths.setCredential(token);
            userAuthsRepository.save(userAuths);
        }
        return token;
    }

    /**
     * 发送注册验证码
     *
     * @param phone
     * @return
     * @throws ServiceException
     */
    public String sendRegisterSmsCode(String phone) throws ServiceException {
        boolean isExist = userAuthsRepository.countByIdentityTypeAndIdentifier(UserAuthType.PHONE.getAuthType(), phone) != 0;
        if (isExist) {
            throw new ServiceException(ServiceExceptionCode.USER_PHONE_HAVE_EXIST, "当前手机号已经注册");
        }
        return saveSmsCode(phone);
    }

    /**
     * 用户通过手机号+验证码注册
     *
     * @param phone
     * @param smsCode
     * @param password
     * @throws ServiceException
     */
    public void userRegisterByPhone(String phone, String smsCode, String password) throws ServiceException {
        boolean isExist = userAuthsRepository.countByIdentityTypeAndIdentifier(UserAuthType.PHONE.getAuthType(), phone) != 0;
        if (isExist) {
            throw new ServiceException(ServiceExceptionCode.USER_PHONE_HAVE_EXIST, "当前手机号已经注册");
        }
        List<SmsCode> smsCodeList = smsCodeRepository.findByPhoneAndTypeAndStatus(phone, SmsCodeType.REGISTER.getType(), 0);
        boolean isCheckSuccess = false;
        for (SmsCode smsCodeItem : smsCodeList) {
            if (smsCodeItem.getSmsCode().equals(smsCode)) {
                Date nowDate = new Date();
                int compareResult = nowDate.compareTo(smsCodeItem.getExpiryTime());
                logger.debug("短信验证码过期时间：" + compareResult);
                // 短信验证码10分钟过期
                if (compareResult > 0) {
                    throw new ServiceException(ServiceExceptionCode.SMS_CODE_EXPIRED, "短信验证码已过期");
                } else {
                    isCheckSuccess = true;
                    break;
                }
            }
        }
        if (!isCheckSuccess) {
            throw new ServiceException(ServiceExceptionCode.SMS_CODE_ERROR, "验证码错误，请重新输入");
        }
        // 短信验证码通过，将短信验证码改为已使用
        for (SmsCode smsCodeItem : smsCodeList) {
            smsCodeItem.setStatus(1);
        }
        smsCodeRepository.saveAll(smsCodeList);
        // 保存用户信息
        String userId = UserIdMaker.makeUserId();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setPhone(phone);
        userInfoRepository.save(userInfo);
        // 保存认证信息
        UserAuths userAuths = new UserAuths();
        userAuths.setUserId(userId);
        userAuths.setIdentityType(UserAuthType.PHONE.getAuthType());
        userAuths.setIdentifier(phone);
        userAuths.setCredential(password);
        userAuthsRepository.save(userAuths);
    }

    /**
     * 关注用户
     *
     * @param userId
     * @param followId
     * @throws ServiceException
     */
    public void followUser(String userId, String followId) throws ServiceException {
        boolean followUserExist = userInfoRepository.countByUserId(followId) > 0;
        if (!followUserExist) {
            throw new ServiceException(ServiceExceptionCode.USER_NO_REGISTER, "关注的用户不存在");
        }
        boolean isFollowed = userFollowRepository.countByUserIdAndFollowId(userId, followId) > 0;
        if (isFollowed) {
            throw new ServiceException(ServiceExceptionCode.USER_NO_REGISTER, "已关注该用户，无需再次关注");
        }
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userId);
        userFollow.setFollowId(followId);
        userFollowRepository.save(userFollow);
    }

    /**
     * 保存短信验证码
     *
     * @param phone
     */
    private String saveSmsCode(String phone) {
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

}
