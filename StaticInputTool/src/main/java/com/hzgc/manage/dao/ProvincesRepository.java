package com.hzgc.manage.dao;

import com.hzgc.manage.entity.Provinces;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 省Repository
 * created by liang on 2018/11/16
 */
public interface ProvincesRepository extends ElasticsearchRepository<Provinces, String> {

}
