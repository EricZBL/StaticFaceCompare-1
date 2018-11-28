package com.hzgc.manage.service.impl;

import com.hzgc.manage.dao.LogRepository;
import com.hzgc.manage.entity.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LogServiceImplTest {

    @Autowired
    private LogRepository logRepository;

    @Test
    public void findPageByXmSfz() {
    }

    @Test
    public void save() {
        Iterable<Log> all = logRepository.findAll();
        System.err.println(all);
    }

    @Test
    public void findPage() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Log> admin = logRepository.findByUsernameLikeOrCreatetimeBetween("admin", "2018-11-26 00:00:00", "2018-11-26 22:00:59", pageable);

        System.err.println(admin);
    }

    @Test
    public void findById() {
    }

    @Test
    public void deleteById() {
    }
}