package com.hzgc.manage.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 人口实体对象
 * created by liang on 2018/11/16
 */
@Document(indexName = "manageperson", type = "Person", shards = 1, replicas = 0)
public class Person implements Serializable {

    /**
     * 人口ID
     */
    @Id
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
     * base64图片（tp）
     */
    private String tp;

    /**
     * 特征值（tzz）
     */
    private String tzz;

    /**
     *  bit特征值（bittzz）
     */
    private String bittzz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSsssqx() {
        return ssssqx;
    }

    public void setSsssqx(String ssssqx) {
        this.ssssqx = ssssqx;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getMlxz() {
        return mlxz;
    }

    public void setMlxz(String mlxz) {
        this.mlxz = mlxz;
    }

    public String getCsd() {
        return csd;
    }

    public void setCsd(String csd) {
        this.csd = csd;
    }

    public String getCym() {
        return cym;
    }

    public void setCym(String cym) {
        this.cym = cym;
    }

    public String getJg() {
        return jg;
    }

    public void setJg(String jg) {
        this.jg = jg;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getTzz() {
        return tzz;
    }

    public void setTzz(String tzz) {
        this.tzz = tzz;
    }

    public String getBittzz() {
        return bittzz;
    }

    public void setBittzz(String bittzz) {
        this.bittzz = bittzz;
    }
}
