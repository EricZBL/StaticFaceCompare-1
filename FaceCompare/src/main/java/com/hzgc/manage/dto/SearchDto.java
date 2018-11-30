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
//String searchId, String tzz, String bittzz, int pageSize, int paegNum
@Data
@ApiModel(value="SearchDto搜图对象",description="搜图对象入参")
public class SearchDto implements Serializable {

    /**
     * 登录账号ID
     */
    @ApiModelProperty(value="登录账号userId",name="userId",example="")
    private String userId;

    @ApiModelProperty(value = "搜索ID", name = "searchId", example = "")
    private String searchId;

    @ApiModelProperty(value = "特征值", name = "tzz", example = "")
    private String tzz;

    /**
     * 身份证（sfz）
     */
//    @ApiModelProperty(value = "bit特征值", name = "bittzz", example = "")
    private String bittzz;

    /**
     * 当前页
     */
//    @ApiModelProperty(value="当前页码",name="page",example="0")
    private Integer page;

    /**
     * 每页大小
     */
//    @ApiModelProperty(value="每页大小",name="size",example="10")
    private Integer size;
}