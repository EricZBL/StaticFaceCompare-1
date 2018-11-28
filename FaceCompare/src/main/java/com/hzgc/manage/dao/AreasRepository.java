package com.hzgc.manage.dao;

import com.hzgc.manage.entity.Areas;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Date;
import java.util.List;

/**
 * 县、区域Repository
 * created by liang on 2018/11/16
 */
public interface AreasRepository extends ElasticsearchRepository<Areas, String> {

    /**
     * 根据城市ID查询区域列表
     */
    List<Areas> findByCityid(String cityid);
}
