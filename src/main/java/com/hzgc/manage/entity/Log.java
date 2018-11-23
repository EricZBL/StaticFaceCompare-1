package com.hzgc.manage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志实体对象
 * created by liang on 2018/11/16
 */
@Document(indexName = "managelog", type = "Log", shards = 1, replicas = 0)
public class Log implements Serializable {

    /**
     * 日志ID
     */
    @Id
    private String id;

    /**
     * 日志名称
     */
    private String logname;

    /**
     * 账号名称
     */
    private String username;

    /**
     * 账号ID
     */
    private String userid;

    /**
     * 性别（xb）
     */
    private String personname;

    /**
     * 民族（mz）
     */
    private String personpic;

    /**
     * 用户创建时间
     */
    @Field( type = FieldType.Date, format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createtime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogname() {
        return logname;
    }

    public void setLogname(String logname) {
        this.logname = logname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getPersonpic() {
        return personpic;
    }

    public void setPersonpic(String personpic) {
        this.personpic = personpic;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Log(){}

    public Log(String userid, String logname) {
        this.logname = logname;
        this.userid = userid;
    }
}
