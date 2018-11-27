package com.hzgc.manage.service;

import com.hzgc.manage.dto.LogQueryDto;
import com.hzgc.manage.dto.PersonQueryDto;
import com.hzgc.manage.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 日志服务接口
 * created by liang on 2018/11/16
 */
public interface LogService {

    /**
     * 返回分页
     */
    Page<Log> findPageByXmSfz(PersonQueryDto personQueryDto, Pageable pageable);

    /**
     * 保存Person实体
     */
    void save(Log log);

    /**
     * 返回所有数据，返回分页
     */
    Page<Log> findPage(LogQueryDto logQueryDto, Pageable pageable);

    /**
     * 根据ID查询单一对象
     */
    Log findById(String id);


    /**
     * 根据ID查询单一对象
     */
    void deleteById(String id);



}
