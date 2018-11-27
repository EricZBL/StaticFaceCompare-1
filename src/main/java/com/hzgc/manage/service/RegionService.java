package com.hzgc.manage.service;

import com.hzgc.manage.entity.Areas;
import com.hzgc.manage.entity.Cities;
import com.hzgc.manage.entity.Provinces;

import java.util.List;

/**
 * 区域服务层接口
 * created by liang on 2018/11/16
 */
public interface RegionService {

    /**
     * 查询省份
     */
    List<Provinces> findProvince();

    /**
     * 根据省份ID查询城市列表
     */
    List<Cities> findByProvinceid(String provinceid);

    /**
     * 根据城市ID查询区域列表
     */
    List<Areas> findByCityid(String cityid);

}
