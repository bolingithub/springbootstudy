package com.example.springbootstudy.controller.dto;

import com.example.springbootstudy.controller.utils.BeanDataConverter;
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
            String[] excludes = {"id", "createTime", "updateTime"};
            BeanDataConverter.converterData(userInfo, userInfoDTO, excludes);
            return userInfoDTO;
        } catch (Exception e) {
            logger.error("数据转换错误，未忽略该属性？：" + e.getMessage());
            throw new ServiceException(ServiceExceptionCode.DEFAULT_ERROR, "系统错误，数据格式转换错误");
        }
    }
}
