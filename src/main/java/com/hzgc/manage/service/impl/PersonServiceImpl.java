package com.hzgc.manage.service.impl;


import com.hzgc.manage.dao.PersonRepository;
import com.hzgc.manage.dto.PersonDto;
import com.hzgc.manage.dto.PersonQueryDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.Person;
import com.hzgc.manage.service.PersonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * created by liang on 18-11-16
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public Page<Person> findPageByXmSfz(PersonQueryDto personQueryDto, Pageable pageable, Log log) {

            return personRepository.findBySfzLikeOrXmLike(personQueryDto.getSfz(),personQueryDto.getXm(),pageable);

    }

    @Override
    public void insert(PersonDto personDto, Log log) {

    }

    @Override
    public void deleteById(String id) {

        personRepository.deleteById(id);

    }

    @Override
    public void update(PersonDto personDto) {

    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Person findById(String id, Log log) {
        if (id == null) {
            return null;
        }
        return personRepository.findById(id).get();
    }

//    @Override
//    public Page<Person> searchByXm(String xm, Pageable pageable) {
//        if (xm == null) return  null;
//        return personRepository.findByXmLike(xm, pageable);
//    }
//
//    @Override
//    public Page<Person> searchBySfz(String sfz, Pageable pageable) {
//        if (sfz == null) return  null;
//        return personRepository.findBySfzLike(sfz, pageable);
//    }
//
//    @Override
//    public Page<Person> search(String sfz, String xm, Pageable pageable) {
//        return personRepository.findBySfzLikeOrXmLike(sfz, xm, pageable);
//    }


}
