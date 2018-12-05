package com.hzgc.exception;

import com.hzgc.manage.enums.ExceptionCodeEnums;
import lombok.Getter;

/**
 * 全局异常定义
 * created by liang on 18-11-20
 */
@Getter
public class HzgcException extends RuntimeException {
    /**
     *  ，状态码
     */
    private Integer code;

    public HzgcException(ExceptionCodeEnums exceptionCodeEnums) {
        super(exceptionCodeEnums.getMsg());
        this.code = exceptionCodeEnums.getCode();
    }

    public HzgcException(ExceptionCodeEnums exceptionCodeEnums , String msg) {
        super(msg);
        this.code = exceptionCodeEnums.getCode();
    }


}
