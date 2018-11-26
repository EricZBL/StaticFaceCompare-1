package com.hzgc.manage.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;


@Document(indexName = "manageareas", type = "Areas", shards = 1, replicas = 0)
public class Areas implements Serializable {

    /*
     * id
     */
    @Id
    private String id;
    /*
     * 县区id
     */
    private String areaid;
    /*
     * 县区名
     */
    private String area;
    /*
     * 所属城市
     */
    private String cityid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }
}
