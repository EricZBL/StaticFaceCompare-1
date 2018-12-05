package com.hzgc.manage.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * 人口对象
 * created by liang on 2018/11/16
 */
@Data
public class PersonVO implements Serializable {

    /**
     * 人口ID
     */
    private String id;

    /**
     * 身份证（sfz）
     */
    private String sfz;

    /**
     * 姓名（xm）
     */
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
     * 图片路径（tp）
     */
    private String tp;

    /**
     * base64图片（tpbase）
     */
    private String tpbase;

    /**
     * 特征值（tzz）
     */
    private String tzz;

    /**
     * bit特征值（bittzz）
     */
    private String bittzz;

    /**
     * 相似度(sim)
     */
    private Float sim;
}