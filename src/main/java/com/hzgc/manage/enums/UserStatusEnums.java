package com.hzgc.manage.enums;

import lombok.Getter;

/**
 * 用户默认密码、状态（启用、禁用）
 * created by liang on 18-11-20
 */
@Getter
public enum UserStatusEnums {

    ENABLE_USER_STATUS(1 , "用户启用"),
    DISABLE_USER_STATUS(2 , "用户禁用"),

    ;

    private Integer code;

    private String msg;

    UserStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

