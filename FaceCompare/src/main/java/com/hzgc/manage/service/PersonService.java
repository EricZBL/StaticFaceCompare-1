package com.hzgc.manage.service;

import com.hzgc.jniface.BigPictureData;
import com.hzgc.manage.dto.PersonDto;
import com.hzgc.manage.dto.PersonQueryDto;
import com.hzgc.manage.dto.SearchDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.Person;
import com.hzgc.manage.vo.SingleSearchResult;
import com.hzgc.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * created by liang on 2018/11/16
 */
public interface PersonService {

    /**
     * 返回分页
     */
    PageUtils<Person> findPageByXmSfz(PersonQueryDto personQueryDto, Pageable pageable, Log log);

    /**
     * 新增Person实体
     */
    void insert(PersonDto personDto, Log log);

    /**
     * 修改Person实体
     */
    void update(PersonDto personDto, Log log);

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
    void deleteById(String id, Log log);


    byte[] getImage(String personid);

    /**
     * 提取特征值
     */
    BigPictureData featureExtractByImage(MultipartFile imageBin, Log log);

    SingleSearchResult search_picture(SearchDto searchDto, Log log);
}
