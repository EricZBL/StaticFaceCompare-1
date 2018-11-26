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
 * 用户实体对象
 * created by liang on 2018/11/16
 */
@Document(indexName = "manageuser", type = "User", shards = 1, replicas = 0)
public class User implements Serializable {

    /**
     * 人口ID
     */
    @Id
    private String id;

    /**
     * 用户姓名（账号名称）
     */
    private String username;

    /**
     * 用户加密密码（账号密码）
     */
    private String password;

    /**
     * 用户状态（账号状态，1 启用  2 禁用）
     */
    private Integer status;

    /**
     * 用户原始密码
     */
    private String originpwd;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOriginpwd() {
        return originpwd;
    }

    public void setOriginpwd(String originpwd) {
        this.originpwd = originpwd;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
