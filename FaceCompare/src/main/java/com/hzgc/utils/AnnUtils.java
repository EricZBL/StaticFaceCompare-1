package com.hzgc.utils;

import io.swagger.annotations.ApiOperation;
import java.lang.reflect.Method;


/**
 * 反射获取注解属性工具类
 * created by liang on 18-11-20
 */
public class AnnUtils {

    /**
     * 反射获取swagger的 @ApiOperation 注解 value值
     */
    public static String getApiValue(String className, String methodName) {

        Class clzz;

        //方法注释值
        String apiOperationValue = "";
        try {
            //通过反射获取到类
            clzz = Class.forName(className);
            //获取类中所有的方法
            Method[] methods = clzz.getDeclaredMethods();

            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                    if (apiOperation != null) {

                        //获取方法上@ApiOperation注解的value值
                        apiOperationValue = apiOperation.value();
                        return apiOperationValue;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return apiOperationValue;
    }

    public static void main(String[] args) {
        String apiValue = getApiValue("com.hzgc.manage.controller.UserController", "login");
        System.err.println(apiValue);
    }
}
