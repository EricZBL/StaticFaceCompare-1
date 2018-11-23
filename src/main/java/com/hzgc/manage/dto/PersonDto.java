package com.hzgc.manage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 人口实体对象 (JSR303参数校验)
 * created by liang on 2018/11/16
 */
@Data
@ApiModel(value="PersonDto人口对象",description="人口对象入参")
public class PersonDto implements Serializable {

    /**
     * 登录账号ID
     */
    @NotEmpty(message = "登录账号userId不能为空")
    @ApiModelProperty(value="登录账号userId",name="userId",example="")
    private String userId;

    /**
     * 身份证（sfz）
     */
    @NotEmpty(message = "用户名不能为空")
    private String sfz;

    /**
     * 姓名（xm）
     */
    @NotEmpty(message = "用户名不能为空")
    private String xm;

    /**
     * 性别（xb）
     */
    private String xb;

    /**
     * 民族（mz）
     */
    private String mz;

    /**
     * 生日（sr）
     */
    private String sr;

    /**
     * （ssssqx）
     */
    private String ssssqx;

    /**
     * 街道（jd）
     */
    private String jd;

    /**
     * 门牌（mp）
     */
    private String mp;

    /**
     * ml现址（mlxz）
     */
    private String mlxz;

    /**
     * 出生地（csd）
     */
    private String csd;

    /**
     * 曾用名（cym）
     */
    private String cym;

    /**
     * 籍贯（jg）
     */
    private String jg;

    /**
     * base64图片（tp）
     */
    private String tp;

}
