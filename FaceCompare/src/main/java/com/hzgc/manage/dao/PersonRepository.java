package com.hzgc.manage.dao;

import com.hzgc.manage.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

/**
 * created by liang on 2018/11/16
 */
public interface PersonRepository extends ElasticsearchRepository<Person, String> {

    /**
     * 按照身份证模糊匹配
     */
    Page<Person> findBySfzLike(String sfz, Pageable pageable);


    /**
     * 按照身份证模糊匹配
     */
    List<Person> findBySfz(String sfz);

    /**
     * 按照姓名模糊匹配
     */
    Page<Person> findByXmLike(String xm, Pageable pageable);

//    Optional<Person> findById(String id);


    //PageUtils<Poem> findByContentLike(@Param("content") String content, Pageable pageable);




}
