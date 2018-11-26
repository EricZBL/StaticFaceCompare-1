package com.hzgc.manage.enums;

import lombok.Getter;

/**
 * 异常状态
 * created by liang on 18-11-20
 */
@Getter
public enum ExceptionCodeEnums {

    PARAM_ERROR(400 , "参数格式不正确"),

    USERID_ISNOT_BLANK(400 , "账号ID不能为空"),

    USERNAME_ISNOT_BLANK(400 , "账号名称不能为空"),

    USERPWD_ISNOT_MATCH(401 , "账号名称或密码不正确"),

    USER_IS_DISABLE(403 , "账号已被禁用"),
    ;

    private Integer code;

    private String msg;

    ExceptionCodeEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

