package com.hzgc.manage.enums;

import lombok.Getter;

/**
 * 结果状态
 * created by liang on 18-11-20
 */
@Getter
public enum ResultCodeEnums {

    OK(200 , "成功"),
    ERROR(-1, "失败");
    /**
     * ×´
     */
    private Integer code;

    /**
     * 状态码消息
     */
    private String msg;

    ResultCodeEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

