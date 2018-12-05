package com.hzgc.manage.service;

import java.io.IOException;


/**
 * created by liang on 2018/11/16
 */
public interface PersonService {

    void insert(String dirPath) throws IOException;

    /**
     * 导入基础数据
     */
    void importdata(String province);
}
