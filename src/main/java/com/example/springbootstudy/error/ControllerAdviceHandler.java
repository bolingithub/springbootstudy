package com.example.springbootstudy.error;

import com.example.springbootstudy.controller.dto.ServiceResult;
import com.example.springbootstudy.error.exception.ServiceException;
import com.example.springbootstudy.error.exception.ServiceExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice  // 不指定包默认加了@Controller和@RestController都能控制
//@ControllerAdvice(basePackages ="com.example.springbootstudy")
public class ControllerAdviceHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdviceHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ServiceResult<Void> errorHandler(HttpServletRequest request, Exception exception) {
        // 将错误日志写到文件中
        writeErrorLogToFile(request, exception);

        // 返回错误结果
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        if (exception instanceof ServiceException) {
            serviceResult.setCode(((ServiceException) exception).getCode());
            serviceResult.setMessage(exception.getMessage());
        } else {
            serviceResult.setCode(ServiceExceptionCode.DEFAULT_ERROR);
            serviceResult.setMessage(exception.getMessage());
        }
        return serviceResult;
    }

    // 将错误日志写到文件中
    private static void writeErrorLogToFile(HttpServletRequest request, Exception exception) {
        logger.error("                              ");
        logger.error("------------------------------");
        logger.error("系统异常 -- 请求地址：" + request.getRequestURI());
        printErrorQuestParameters(request.getParameterMap());
        logger.error("系统异常 -- 具体错误：" + exception.getMessage());
        logger.error("------------------------------");
    }

    // 打印系统异常时的请求参数
    private static void printErrorQuestParameters(Map<String, String[]> parameterMap) {
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            logger.error("系统异常 -- 请求参数：" + entry.getKey() + " -- 请求值：" + forEachStringList(entry.getValue()));
        }
    }

    private static String forEachStringList(String[] values) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : values) {
            stringBuilder.append(item);
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }

}
