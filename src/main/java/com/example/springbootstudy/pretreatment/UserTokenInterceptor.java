package com.example.springbootstudy.pretreatment;

import com.example.springbootstudy.error.exception.ServiceException;
import com.example.springbootstudy.error.exception.ServiceExceptionCode;
import com.example.springbootstudy.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserTokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(UserTokenInterceptor.class);

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getParameter("userId");
        String token = request.getParameter("token");
        if (userId == null || userId.isEmpty()) {
            throw new ServiceException(ServiceExceptionCode.PARAMS_ERROR, "缺少userId");
        }
        if (token == null || token.isEmpty()) {
            throw new ServiceException(ServiceExceptionCode.PARAMS_ERROR, "缺少token");
        }
        logger.debug("userId:" + userId + " token:" + token);
        boolean isTokenOk = userService.checkoutToken(userId, token);
        if (!isTokenOk) {
            throw new ServiceException(ServiceExceptionCode.TOKEN_ERROR, "用户认证信息错误，请重新登陆");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.debug("postHandle");
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        logger.debug("afterCompletion");
    }
}













