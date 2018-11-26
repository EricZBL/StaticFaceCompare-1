package com.hzgc.manage.dao;

import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Date;

/**
 * 用户Repository
 * created by liang on 2018/11/16
 */
public interface LogRepository extends ElasticsearchRepository<Log, String> {

    /**
     * 按照账号姓名模糊匹配
     */
    Page<Log> findByUsername(String username, Pageable pageable);

    /**
     * 按照姓名、时间匹配
     */
    User findByUsernameAndCreatetime(String username, Date password);
}
