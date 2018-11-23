package com.hzgc.manage.service.impl;

import com.hzgc.manage.dao.*;
import com.hzgc.manage.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void insert() {

        List<Person> list = new ArrayList<>();

        for(int i = 0; i < 50000; i ++){

        Person person = new Person();

        person.setId(UUID.randomUUID().toString());

        person.setXm(RandomValue.getChineseName());

        person.setXb(RandomValue.getSex());

        person.setSfz(RandomValue.getSfz());

        person.setMz("汉族");

        person.setSr(RandomValue.getSr());

        person.setSsssqx(RandomValue.getssssqx());

        person.setMlxz(RandomValue.getMLXZ());

        person.setJd(RandomValue.getStreet());

        person.setMp(RandomValue.getStreet());

        person.setJg(RandomValue.getssssqx());

        person.setTp("/opt/1.jpg");

        person.setBittzz("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");

        person.setTzz("Fb6JV0ucI87NskXQ7k6FLHnSTmhQXTjwBMWqktsk/bc=");

        list.add(person);

        }
        long l = System.currentTimeMillis();
        System.err.println(l);
        personRepository.saveAll(list);

        long ll = System.currentTimeMillis();
        System.err.println(ll);

        System.err.println(ll-l);


    }

    @Test
    public void save() {

    }

}