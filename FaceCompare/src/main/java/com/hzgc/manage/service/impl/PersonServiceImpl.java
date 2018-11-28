package com.hzgc.manage.service.impl;


import cn.hutool.core.util.IdUtil;
import com.hzgc.exception.HzgcException;
import com.hzgc.manage.dao.PersonRepository;
import com.hzgc.manage.dto.PersonDto;
import com.hzgc.manage.dto.PersonQueryDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.Person;
import com.hzgc.manage.enums.ExceptionCodeEnums;
import com.hzgc.manage.service.LogService;
import com.hzgc.manage.service.PersonService;
import com.hzgc.utils.Base64Utils;
import com.hzgc.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by liang on 18-11-16
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LogService logService;


    @Override
    public PageUtils<Person> findPageByXmSfz(PersonQueryDto personQueryDto, Pageable pageable, Log log) {

            Page<Person> personPage = this.findPersonPage(personQueryDto, pageable);

            PageUtils<Person> page = new PageUtils<>();
            page.setNumber(personPage.getNumber()+1);
            page.setSize(personPage.getSize());
            page.setTotalElements(personPage.getTotalElements());

            List<Person> content = personPage.getContent();

    //        content.stream().forEach(e -> {
    //            e.setTpbase(Base64Utils.getImageStr(e.getTp()));
    //        });
            for (Person  person : content) {
                String tp = person.getTp();
                person.setTpbase(Base64Utils.getImageStr(tp));
            }
            page.setContent(content);
        return page;

    }

    @Override
    public void insert(PersonDto personDto, Log log) {
        String idcard = personDto.getSfz();
        String name = personDto.getXm();
        if (StringUtils.isBlank(idcard)) throw new HzgcException(ExceptionCodeEnums.PARAM_ERROR);
        if (StringUtils.isBlank(name)) throw new HzgcException(ExceptionCodeEnums.PARAM_ERROR);
        Person person = new Person();
        person.setId(IdUtil.simpleUUID());
        person.setSfz(idcard);
        person.setXm(name);
        person.setXb(personDto.getXb());
        person.setMz(personDto.getMz());
        person.setSr(personDto.getSr());
        person.setSsssqx(personDto.getSsssqx());
        person.setJd(personDto.getJd());
        person.setMp(personDto.getMp());
        person.setMlxz(personDto.getMlxz());
        person.setCsd(personDto.getCsd());
        person.setCym(personDto.getCym());
        person.setJg(personDto.getJg());
        person.setTp(personDto.getTp());
        personRepository.save(person);
    }

    @Override
    public void deleteById(String id) {
        if (StringUtils.isNotBlank(id))  throw new HzgcException(ExceptionCodeEnums.PARAM_ERROR);
        personRepository.deleteById(id);

    }

    @Override
    public void update(PersonDto personDto) {
        Person person = new Person();
        person.setId(personDto.getPeopleId());
        person.setSfz(personDto.getSfz());
        person.setXm(personDto.getXm());
        person.setXb(personDto.getXb());
        person.setMz(personDto.getMz());
        person.setSr(personDto.getSr());
        person.setSsssqx(personDto.getSsssqx());
        person.setJd(personDto.getJd());
        person.setMp(personDto.getMp());
        person.setMlxz(personDto.getMlxz());
        person.setCsd(personDto.getCsd());
        person.setCym(personDto.getCym());
        person.setJg(personDto.getJg());
        person.setTp(personDto.getTp());
        this.save(person);
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
//    public PageUtils<Person> searchByXm(String xm, Pageable pageable) {
//        if (xm == null) return  null;
//        return personRepository.findByXmLike(xm, pageable);
//    }
//
//    @Override
//    public PageUtils<Person> searchBySfz(String sfz, Pageable pageable) {
//        if (sfz == null) return  null;
//        return personRepository.findBySfzLike(sfz, pageable);
//    }
//
//    @Override
//    public PageUtils<Person> search(String sfz, String xm, Pageable pageable) {
//        return personRepository.findBySfzLikeOrXmLike(sfz, xm, pageable);
//    }


    @Override
    public byte[] getImage(String personid) {

        Person person = personRepository.findById(personid).get();
        String tp = person.getTp();

        byte[] image;
        if (StringUtils.isNotBlank(tp)) {
            return Base64Utils.base64Str2BinArry(Base64Utils.getImageStr(tp));
        } else {
            return image = new byte[0];
        }
    }

    private Page<Person> findPersonPage(PersonQueryDto personQueryDto, Pageable pageable){

        String name = personQueryDto.getXm();
        String idCard =personQueryDto.getSfz();
        if (StringUtils.isNotBlank(name)){
            return personRepository.findByXmLike("*"+name+"*",pageable);
        }
        if (StringUtils.isNotBlank(idCard)){
            return personRepository.findBySfzLike("*"+idCard+"*",pageable);
        }
        return personRepository.findAll(pageable);
    }

}
