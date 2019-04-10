package com.example.springbootstudy.controller.dto;

import com.example.springbootstudy.controller.utils.BeanDataConverter;
import com.example.springbootstudy.database.entity.UserInfo;
import com.example.springbootstudy.services.exception.ServiceException;
import com.example.springbootstudy.services.exception.ServiceExceptionCode;

public class DTOFactory {

    public UserInfoDTO userInfo2DTO(UserInfo userInfo) throws ServiceException {
        try {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            String[] excludes = {"createTime", "updateTime"};
            BeanDataConverter.converterData(userInfo, userInfoDTO, excludes);
            return userInfoDTO;
        } catch (Exception e) {
            throw new ServiceException(ServiceExceptionCode.DEFAULT_ERROR, "系统错误，数据格式转换错误");
        }
    }
}
