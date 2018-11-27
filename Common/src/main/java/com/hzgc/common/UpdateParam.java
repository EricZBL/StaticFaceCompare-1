package com.hzgc.common;

public class UpdateParam {
    //数据存在es中的Id
    private String esId;
    //比特位特征值
    private String bitFeature;
    //身份证号
    private String idCard;

    public UpdateParam(String esId, String bitFeature, String idCard) {
        this.esId = esId;
        this.bitFeature = bitFeature;
        this.idCard = idCard;
    }

    public String getEsId() {
        return esId;
    }

    public void setEsId(String esId) {
        this.esId = esId;
    }

    public String getBitFeature() {
        return bitFeature;
    }

    public void setBitFeature(String bitFeature) {
        this.bitFeature = bitFeature;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
