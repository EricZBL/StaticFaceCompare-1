package com.hzgc.manage.dao;

import com.hzgc.manage.entity.Cities;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 城市Repository
 * created by liang on 2018/11/16
 */
public interface CitiesRepository extends ElasticsearchRepository<Cities, String> {

}
