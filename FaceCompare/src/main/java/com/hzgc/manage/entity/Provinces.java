package com.hzgc.manage.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "manageprovinces", type = "Provinces", shards = 1, replicas = 0)
public class Provinces implements Serializable {

    /*
     * id
     */
    @Id
    private String id;
    /*
     * 省份id
     */
    private String provinceid;
    /*
     * 省份名
     */
    private String province;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
