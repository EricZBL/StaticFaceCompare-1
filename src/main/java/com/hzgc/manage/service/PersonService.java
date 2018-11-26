package com.hzgc.manage.service;

import com.hzgc.manage.dto.PersonDto;
import com.hzgc.manage.dto.PersonQueryDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * created by liang on 2018/11/16
 */
public interface PersonService {

    /**
     * 返回分页
     */
    Page<Person> findPageByXmSfz(PersonQueryDto personQueryDto, Pageable pageable, Log log);

    /**
     * 新增Person实体
     */
    void insert(PersonDto personDto, Log log);

    /**
     * 修改Person实体
     */
    void update(PersonDto personDto);

    /**
     * 保存Person实体
     */
    void save(Person person);

    /**
     * 返回所有数据，返回分页
     */
    Page<Person> findAll(Pageable pageable);

    /**
     * 根据ID查询单一对象
     */
    Person findById(String id, Log log);


    /**
     * 根据ID查询单一对象
     */
    void deleteById(String id);


}
