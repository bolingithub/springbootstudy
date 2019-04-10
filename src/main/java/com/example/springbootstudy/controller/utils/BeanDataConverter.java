package com.example.springbootstudy.controller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class BeanDataConverter {

//    private static final Logger logger = LoggerFactory.getLogger(BeanDataConverter.class);

    /**
     * 数据转换工具，例如将entity转为dto
     *
     * @param fromBean
     * @param toBean
     * @param excludeProperties
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    public static void converterData(Object fromBean, Object toBean, String[] excludeProperties)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Objects.requireNonNull(fromBean);
        Objects.requireNonNull(toBean);
        Objects.requireNonNull(excludeProperties);

        List<String> excludes = Arrays.stream(excludeProperties).map(String::toLowerCase).collect(Collectors.toList());

        Method[] methods = fromBean.getClass().getMethods();
        for (Method method : methods) {

            String methodName = method.getName();

//            logger.info("methodName:" + methodName);
            System.out.println("methodName:" + methodName);

            if (!methodName.startsWith("get") || "getClass".equals(methodName)
                    || excludes.contains(methodName.replaceFirst("get", "").toLowerCase())) {
                continue;
            }
            Class<?> returnType = method.getReturnType();
            Object value = method.invoke(fromBean);
            String setMethodName = String.format("set%s", methodName.replaceFirst("get", ""));
            Method setMethod = toBean.getClass().getMethod(setMethodName, returnType);
            setMethod.invoke(toBean, value);
        }
    }

}
