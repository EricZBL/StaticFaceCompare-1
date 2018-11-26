package com.hzgc.bean;

import java.io.Serializable;

public class PersonAttributeValue implements Serializable {
    private  String desc;
    private  String code;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
