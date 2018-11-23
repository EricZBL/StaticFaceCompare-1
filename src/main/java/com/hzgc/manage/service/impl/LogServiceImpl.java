package com.hzgc.manage.service.impl;


import com.hzgc.manage.dao.LogRepository;
import com.hzgc.manage.dto.LogQueryDto;
import com.hzgc.manage.dto.PersonQueryDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public Log findById(String id) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
