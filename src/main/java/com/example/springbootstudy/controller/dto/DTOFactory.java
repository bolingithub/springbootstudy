package com.example.springbootstudy.controller.dto;

import com.example.springbootstudy.controller.utils.BeanDataConverter;
import com.example.springbootstudy.database.entity.Notify;
import com.example.springbootstudy.database.entity.UserInfo;
import com.example.springbootstudy.error.exception.ServiceException;
import com.example.springbootstudy.error.exception.ServiceExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DTOFactory {

    private static final Logger logger = LoggerFactory.getLogger(DTOFactory.class);

    public static UserInfoDTO userInfo2DTO(UserInfo userInfo) throws ServiceException {
        try {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            String[] excludes = {"id", "createTime", "updateTime", "token"};
            BeanDataConverter.converterData(userInfo, userInfoDTO, excludes);
            return userInfoDTO;
        } catch (Exception e) {
            logger.error("数据转换错误，未忽略该属性？：" + e.getMessage());
            throw new ServiceException(ServiceExceptionCode.DEFAULT_ERROR, "系统错误，数据格式转换错误");
        }
    }

    public static UserFanInfoDTO userFanInfo2DTO(UserInfo userInfo) throws ServiceException {
        try {
            UserFanInfoDTO userFanInfoDTO = new UserFanInfoDTO();
            String[] excludes = {"id", "createTime", "updateTime", "phone", "realName", "personalId"};
            BeanDataConverter.converterData(userInfo, userFanInfoDTO, excludes);
            return userFanInfoDTO;
        } catch (Exception e) {
            logger.error("数据转换错误，未忽略该属性？：" + e.getMessage());
            throw new ServiceException(ServiceExceptionCode.DEFAULT_ERROR, "系统错误，数据格式转换错误");
        }
    }

    public static NotifyDTO notify2DTO(Notify notify) throws ServiceException {
        try {
            NotifyDTO notifyDTO = new NotifyDTO();
            String[] excludes = {"userId", "type", "status"};
            BeanDataConverter.converterData(notify, notifyDTO, excludes);
            return notifyDTO;
        } catch (Exception e) {
            logger.error("数据转换错误，未忽略该属性？：" + e.getMessage());
            throw new ServiceException(ServiceExceptionCode.DEFAULT_ERROR, "系统错误，数据格式转换错误");
        }
    }
}
