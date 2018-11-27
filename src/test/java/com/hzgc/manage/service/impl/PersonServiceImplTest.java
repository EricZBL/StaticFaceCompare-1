package com.hzgc.manage.service.impl;

import com.hzgc.manage.dao.PersonRepository;
import com.hzgc.manage.entity.Person;
import com.hzgc.manage.service.PersonService;
import javafx.scene.control.Pagination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * created by liang on 18-11-20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonServiceImplTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void save() {

        for(int i =0; i < 100; i++){

            Person person = new Person();
            person.setId(UUID.randomUUID().toString());
            person.setXm("两赛季" + i);
            person.setSfz("34417554246545" + i);

            personService.save(person);

        }
    }

    @Test
    public void searchBySfz() {
    }

    @Test
    public void search() {

    }

    @Test
    public void findAll() {

    }

    @Test
    public void findById() {
        //40250008

        long count = personRepository.count();
        System.err.println(count);

        return;
    }

    @Test
    public void findPageByXmSfz() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Person> list = personRepository.findByXmLike("张三", pageable);

        Page<Person> bySfzLike = personRepository.findBySfzLike("61042519920319093X", pageable);

        System.err.println(bySfzLike);
    }
}