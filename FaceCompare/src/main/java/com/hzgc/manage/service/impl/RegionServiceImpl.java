package com.hzgc.manage.service.impl;

import com.hzgc.manage.dao.AreasRepository;
import com.hzgc.manage.dao.CitiesRepository;
import com.hzgc.manage.dao.ProvincesRepository;
import com.hzgc.manage.entity.Areas;
import com.hzgc.manage.entity.Cities;
import com.hzgc.manage.entity.Provinces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by liang on 18-11-16
 */
@Service
public class RegionServiceImpl implements com.hzgc.manage.service.RegionService {

    @Autowired
    private ProvincesRepository provincesRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private AreasRepository areasRepository;

    @Override
    public List<Provinces> findProvince() {

        Iterable<Provinces> provincesIterable = provincesRepository.findAll();
        List<Provinces> list = new ArrayList<>();
        provincesIterable.forEach(e ->list.add(e));
        return list;
    }

    @Override
    public List<Cities> findByProvinceid(String provinceid) {
        Iterable<Cities> citiesIterable = citiesRepository.findByProvinceid(provinceid);
        List<Cities> list = new ArrayList<>();
        citiesIterable.forEach(e ->list.add(e));
        return list;
    }

    @Override
    public List<Areas> findByCityid(String cityid) {
        Iterable<Areas> areasIterable = areasRepository.findByCityid(cityid);
        List<Areas> list = new ArrayList<>();
        areasIterable.forEach(e -> list.add(e));
        return list;
    }
}
