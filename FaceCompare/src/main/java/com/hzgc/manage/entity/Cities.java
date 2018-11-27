package com.hzgc.manage.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "managecities", type = "Cities", shards = 1, replicas = 0)
public class Cities implements Serializable {

    /*
     * id
     */
    @Id
    private String id;
    /*
     * 城市id
     */
    private String cityid;
    /*
     * 城市名
     */
    private String city;
    /*
     * 所属省份id
     */
    private String provinceid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }
}
