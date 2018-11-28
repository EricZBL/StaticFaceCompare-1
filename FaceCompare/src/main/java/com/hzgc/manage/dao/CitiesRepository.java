package com.hzgc.manage.dao;

import com.hzgc.manage.entity.Cities;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 城市Repository
 * created by liang on 2018/11/16
 */
public interface CitiesRepository extends ElasticsearchRepository<Cities, String> {

    /**
     * 根据省份ID查询城市列表
     */
    List<Cities> findByProvinceid(String provinceid);
}
