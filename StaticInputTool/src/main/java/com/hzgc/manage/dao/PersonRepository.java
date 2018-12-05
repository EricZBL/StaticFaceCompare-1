package com.hzgc.manage.dao;

import com.hzgc.manage.entity.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * created by liang on 2018/11/16
 */
public interface PersonRepository extends ElasticsearchRepository<Person, String> {

}
