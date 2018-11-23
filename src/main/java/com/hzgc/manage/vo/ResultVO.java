package com.hzgc.manage.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回给前端的数据封装
 * created by liang on 18-11-20
 */
@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = -4414926978700453869L;

    /**
     * 状态马
     */
    private Integer code;

    /**
     * 状态说明
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

}