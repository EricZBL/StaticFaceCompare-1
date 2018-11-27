package com.hzgc.manage.service.impl;


import cn.hutool.core.date.DateUtil;
import com.hzgc.manage.dao.LogRepository;
import com.hzgc.manage.dto.LogQueryDto;
import com.hzgc.manage.dto.PersonQueryDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.User;
import com.hzgc.manage.service.LogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date;

/**
 * 日志服务层实现
 * created by liang on 18-11-16
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public Page<Log> findPageByXmSfz(PersonQueryDto personQueryDto, Pageable pageable) {
        return null;
    }

    @Override
    public void save(Log log) {
        logRepository.save(log);
    }

    @Override
    public Page<Log> findPage(LogQueryDto logQueryDto, Pageable pageable) {


        if(StringUtils.isNotBlank(logQueryDto.getUsername()) || logQueryDto.getCreatetime() != null){

            if(StringUtils.isNotBlank(logQueryDto.getUsername()) && logQueryDto.getCreatetime() != null){

                Date createtime = logQueryDto.getCreatetime();
                String starttime = DateUtil.format(DateUtil.beginOfDay(createtime), "yyyy-MM-dd HH:mm:ss");
                String endtime = DateUtil.format(DateUtil.endOfDay(createtime), "yyyy-MM-dd HH:mm:ss");
                return logRepository.findByUsernameAndCreatetimeBetween(logQueryDto.getUsername(), starttime, endtime, pageable);

                }else {
                Date createtime = logQueryDto.getCreatetime();
                String starttime = DateUtil.format(DateUtil.beginOfDay(createtime), "yyyy-MM-dd HH:mm:ss");
                String endtime = DateUtil.format(DateUtil.endOfDay(createtime), "yyyy-MM-dd HH:mm:ss");
                return logRepository.findByUsernameOrCreatetimeBetween(logQueryDto.getUsername(), starttime, endtime, pageable);
            }
        }
        return  logRepository.findAll(pageable);
    }

    @Override
    public Log findById(String id) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
