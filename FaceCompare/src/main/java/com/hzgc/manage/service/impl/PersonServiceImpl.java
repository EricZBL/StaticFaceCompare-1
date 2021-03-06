package com.hzgc.manage.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import com.hzgc.common.CompareParam;
import com.hzgc.common.SearchResult;
import com.hzgc.config.HzgcConfig;
import com.hzgc.exception.HzgcException;
import com.hzgc.jniface.*;
import com.hzgc.manage.dao.MemoryDao;
import com.hzgc.manage.dao.PersonRepository;
import com.hzgc.manage.dto.PersonDto;
import com.hzgc.manage.dto.PersonQueryDto;
import com.hzgc.manage.dto.SearchDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.Person;
import com.hzgc.manage.entity.User;
import com.hzgc.manage.enums.ExceptionCodeEnums;
import com.hzgc.manage.service.LogService;
import com.hzgc.manage.service.PersonService;
import com.hzgc.manage.service.UserService;
import com.hzgc.manage.vo.PersonVO;
import com.hzgc.manage.vo.SingleSearchResult;
import com.hzgc.utils.Base64Utils;
import com.hzgc.utils.ImageUtil;
import com.hzgc.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * created by liang on 18-11-16
 */
@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    @Autowired
    private HzgcConfig hzgcConfig;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LogService logsService;

    @Autowired
    private UserService userService;

    @Autowired
    private Client client;

    @Autowired
    private MemoryDao memoryDao;


    @Override
    public PageUtils<Person> findPageByXmSfz(PersonQueryDto personQueryDto, Pageable pageable, Log logs) {

        Page<Person> personPage = this.findPersonPage(personQueryDto, pageable);

        PageUtils<Person> page = new PageUtils<>();
        page.setNumber(personPage.getNumber() + 1);
        page.setSize(personPage.getSize());
        page.setTotalElements(personPage.getTotalElements());

        log.info("findPageByXmSfz num :" + (personPage.getNumber() + 1) + ", size:" + personPage.getSize() + ", total : " + personPage.getTotalElements());
        List<Person> content = personPage.getContent();

        for (Person person : content) {
            String tp = person.getTp();
            if (StringUtils.isBlank(tp)) {
                continue;
            }
            try {
                person.setTpbase(Base64Utils.getImageStr(tp));
            } catch (Exception e) {
                log.error(person.getId() + ":" + e.getMessage());
            }
        }
        page.setContent(content);

        //写入日志
        this.insertLog(logs);
        return page;
    }

    @Override
    @Transactional
    public void insert(PersonDto personDto, Log logs) {

        String idcard = personDto.getSfz();
        String name = personDto.getXm();
        if (StringUtils.isBlank(idcard)) throw new HzgcException(ExceptionCodeEnums.PARAM_ERROR);
        if (StringUtils.isBlank(name)) throw new HzgcException(ExceptionCodeEnums.PARAM_ERROR);

        Person person = new Person();
        BeanUtil.copyProperties(personDto, person);

        person.setId(IdUtil.simpleUUID());

        if (personDto.getSfz().length() != 18) throw new HzgcException(ExceptionCodeEnums.IDCARD_FORMAT_ERROR);

        List<Person> personList = personRepository.findBySfz(person.getSfz());
        if (personList.size() > 0) throw new HzgcException(ExceptionCodeEnums.PERSON_EXIST_ERROR);

        String province = personDto.getSfz().substring(0, 2);
        String city = personDto.getSfz().substring(2, 4);
        String town = personDto.getSfz().substring(4, 6);
        Short year = IdcardUtil.getYearByIdCard(personDto.getSfz());
        String month = personDto.getSfz().substring(10, 12);
        String path = hzgcConfig.getDir() + "/" + province + "/" + city + "/" + town + "/" + year + "/" + month + "/" + personDto.getSfz() + "/" + person.getId() + ".jpeg";
        log.info("insert personid : " + person.getId() + " , dir path : " + path);
//        String path = "D:\\" + province + "\\" + city + "\\" + town + "\\" + year + "\\" + month + "\\" + personDto.getSfz() + "\\" + person.getId() + ".jpeg";
        String tp = personDto.getTp();

        if (StringUtils.isNotBlank(tp) && StringUtils.isNotBlank(path)) {
            byte[] bytes = Base64Utils.base64Str2BinArry(tp);
            ImageUtil.save(path, bytes);
            person.setTp(path);

            //TODO提取特征值
            FaceAttribute faceAttribute = FaceFunction.faceFeatureExtract(Base64Utils.base64Str2BinArry(tp), PictureFormat.JPG);
            log.info(faceAttribute.toString());
            String bittzz = FaceUtil.bitFeautre2Base64Str(faceAttribute.getBitFeature());
            String ttz = FaceUtil.floatFeature2Base64Str(faceAttribute.getFeature());
            if (StringUtils.isBlank(bittzz) || StringUtils.isBlank(ttz))
                throw new HzgcException(ExceptionCodeEnums.IMAGE_FORMAT_ERROR);
            person.setTzz(ttz);
            person.setBittzz(bittzz);
            log.info(ttz + "=======" + bittzz);

        }
        personRepository.save(person);
        client.addData(person.getId(), person.getBittzz(), person.getSfz());
        //写入日志
        this.insertLog(logs);
    }

    @Override
    public void deleteById(String id, Log logs) {
        if (StringUtils.isBlank(id)) throw new HzgcException(ExceptionCodeEnums.PARAM_ERROR);
        log.info("delete id: " + id);
        //写入日志
        this.insertLog(logs);
//        client.delete(id,personRepository.findById(id).get().getSfz());
        personRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(PersonDto personDto, Log logs) {
        personRepository.deleteById(personDto.getPeopleId());
        List<Person> personList = personRepository.findBySfz(personDto.getSfz());
        if (personList.size() > 0) throw new HzgcException(ExceptionCodeEnums.PERSON_EXIST_ERROR);

        if (StringUtils.isBlank(personDto.getPeopleId()))
            throw new HzgcException(ExceptionCodeEnums.PERSONID_ISNOT_ERROR);
        Person person = new Person();
        BeanUtil.copyProperties(personDto, person);
        person.setId(IdUtil.simpleUUID());

        if (personDto.getSfz().length() != 18) throw new HzgcException(ExceptionCodeEnums.IDCARD_FORMAT_ERROR);
        String province = personDto.getSfz().substring(0, 2);
        String city = personDto.getSfz().substring(2, 4);
        String town = personDto.getSfz().substring(4, 6);
        Short year = IdcardUtil.getYearByIdCard(personDto.getSfz());
        String month = personDto.getSfz().substring(10, 12);
        String path = hzgcConfig.getDir() + "/" + province + "/" + city + "/" + town + "/" + year + "/" + month + "/" + personDto.getSfz() + "/" + person.getId() + ".jpeg";
        log.info("update personid : " + person.getId() + " , update path : " + path);
//        String path = "D:\\" + province + "\\" + city + "\\" + town + "\\" + year + "\\" + month + "\\" + personDto.getSfz() + "\\" + person.getId() + ".jpeg";
        String tp = personDto.getTp();
        if (StringUtils.isNotBlank(tp) && StringUtils.isNotBlank(path)) {

            byte[] bytes = Base64Utils.base64Str2BinArry(tp);
            ImageUtil.save(path, bytes);
            person.setTp(path);
            //提取特征值
            FaceAttribute faceAttribute = FaceFunction.faceFeatureExtract(Base64Utils.base64Str2BinArry(tp), PictureFormat.JPG);
            String bittzz = FaceUtil.bitFeautre2Base64Str(faceAttribute.getBitFeature());
            String ttz = FaceUtil.floatFeature2Base64Str(faceAttribute.getFeature());
            if (StringUtils.isBlank(bittzz) || StringUtils.isBlank(ttz))
                throw new HzgcException(ExceptionCodeEnums.IMAGE_FORMAT_ERROR);
            person.setTzz(ttz);
            person.setBittzz(bittzz);
        }
        personRepository.save(person);
        client.addData(person.getId(), person.getBittzz(), person.getSfz());

        //写入日志
        this.insertLog(logs);
        client.delete(personDto.getPeopleId(), personDto.getSfz());

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
    public Person findById(String id, Log logs) {
        if (id == null) {
            return null;
        }
        return personRepository.findById(id).get();
    }

    @Override
    public BigPictureData featureExtractByImage(MultipartFile image, Log logs) {

        byte[] imageBytes = null;
        try {
            imageBytes = image.getBytes();
        } catch (IOException e) {
            log.error("image to Bytes has IO Exception");
        }
        String imageType = null;
        BigPictureData bigPictureData = new BigPictureData();
        ArrayList<PictureData> smallPictures = new ArrayList<>();
        ArrayList<SmallImage> smallImages = FaceFunction.faceCheck(imageBytes, PictureFormat.JPG, PictureFormat.LEVEL_WIDTH_3);
        log.info("smallImages.size(): " + smallImages.size() );
        if (null != smallImages && smallImages.size() > 0) {
            for (SmallImage smallImage : smallImages) {
                PictureData pictureData = new PictureData();
                pictureData.setImageData(smallImage.getPictureStream());
                pictureData.setImageID(IdUtil.simpleUUID());
                pictureData.setFeature(smallImage.getFaceAttribute());
                pictureData.setImage_coordinate(smallImage.getFaceAttribute().getImage_coordinate());
                imageType = smallImage.getImageType();
                smallPictures.add(pictureData);
            }
            bigPictureData.setImageType(imageType);
            bigPictureData.setSmallImages(smallPictures);
            bigPictureData.setTotal(smallPictures.size());
            bigPictureData.setImageID(IdUtil.simpleUUID());
            bigPictureData.setImageData(imageBytes);
            //写日志
            logs.setPersonpic(Base64Utils.getImageStr(imageBytes));
            this.insertLog(logs);

            return bigPictureData;
        }
        log.info("No face in the picture");
        return bigPictureData;
    }

    @Override
    public SingleSearchResult search_picture(SearchDto searchDto, Log logs) {
        Integer page = searchDto.getPage();
        Integer size = searchDto.getSize();
        PersonServiceImpl.log.info("PageSize : " + page + " Size : " + size);
        if (StringUtils.isNotBlank(searchDto.getSearchId())) {
            PersonServiceImpl.log.info("Search Id is not null , search from memeory");
            SingleSearchResult singleSearchResult = memoryDao.getSearchRes(searchDto.getSearchId());
            SingleSearchResult singleSearchResult2 = new SingleSearchResult();
            List<PersonVO> list = singleSearchResult.getPersonVOS();
            List<PersonVO> personVOList = new ArrayList<>();

            for (int i = size * (page - 1); i < size * page; i++) {
                if (i < singleSearchResult.getTotal()) {
                    personVOList.add(list.get(i));
                }
            }
            singleSearchResult2.setSearchId(singleSearchResult.getSearchId());
            singleSearchResult2.setTotal(singleSearchResult.getTotal());
            singleSearchResult2.setPersonVOS(personVOList);
            return singleSearchResult2;
        }
        PersonServiceImpl.log.info("start compare with bittzz and tzz, sim is " + hzgcConfig.getSim());
        PersonServiceImpl.log.info("bittzz is :" + Arrays.toString(searchDto.getBittzz()));
        PersonServiceImpl.log.info("tzz is :" + Arrays.toString(searchDto.getTzz()));

        CompareParam compareParam = new CompareParam(searchDto.getBittzz(), searchDto.getTzz(), hzgcConfig.getSim());
        SearchResult compareresult =new SearchResult();
        try{
         compareresult = client.compare(compareParam);
        }catch (Exception e){
            log.error("exception:"+e.getMessage());
            throw new HzgcException(ExceptionCodeEnums.COMPARE_ERROR);
        }
        SearchResult.Record[] records = compareresult.getRecords();
        log.info("length::"+records.length);
        SingleSearchResult singleSearchResult = new SingleSearchResult();
        List<PersonVO> personVOS = new ArrayList<>();
        for (SearchResult.Record record : records) {
            try {
                com.hzgc.common.Person person = (com.hzgc.common.Person) record.getValue();
                PersonVO personVO = new PersonVO();
                BeanUtil.copyProperties(person, personVO);
                personVO.setSim(record.getKey());
                personVO.setTpbase(Base64Utils.getImageStr(person.getTp()));
                personVOS.add(personVO);
            } catch (Exception e) {
                PersonServiceImpl.log.error("Value " + record.getValue().toString());
                PersonServiceImpl.log.error("Key " + record.getKey());
            }
        }
        singleSearchResult.setPersonVOS(personVOS);
        singleSearchResult.setSearchId(IdUtil.simpleUUID());
        singleSearchResult.setTotal(records.length);

        PersonServiceImpl.log.info("insert result into memory");
        memoryDao.insertSearchRes(singleSearchResult);
        SingleSearchResult searchResult = new SingleSearchResult();
        searchResult.setSearchId(singleSearchResult.getSearchId());
        searchResult.setTotal(singleSearchResult.getTotal());
        List<PersonVO> personVOList = new ArrayList<>();
        for (int i = size * (page - 1); i < size * page; i++) {
            if (i < singleSearchResult.getTotal()) {
                personVOList.add(personVOS.get(i));
            }
        }
        searchResult.setPersonVOS(personVOList);
//        this.insertLog(log);
        return searchResult;
    }


    @Override
    public byte[] getImage(String personid) {

        Person person = personRepository.findById(personid).get();
        String tp = person.getTp();

        byte[] image;
        if (StringUtils.isNotBlank(tp)) {
            return Base64Utils.base64Str2BinArry(Base64Utils.getImageStr(tp));
        } else {
            log.info("no ");
            return image = new byte[0];
        }
    }

    private Page<Person> findPersonPage(PersonQueryDto personQueryDto, Pageable pageable) {

        String name = personQueryDto.getXm();
        String idCard = personQueryDto.getSfz();
        if (StringUtils.isNotBlank(name)) {
            log.info("idcard is blank , search by name: " + name);
            return personRepository.findByXmLike("*" + name + "*", pageable);
        }
        if (StringUtils.isNotBlank(idCard)) {
            log.info("name is blank , search by idcard " + idCard);
            return personRepository.findBySfzLike("*" + idCard + "*", pageable);
        }
        return personRepository.findAll(pageable);
    }

    private void insertLog(Log logs) {
        logs.setId(IdUtil.simpleUUID());
        User user = userService.selectOneById(logs.getUserid());
        logs.setUsername(user.getUsername());
        logs.setCreatetime(new Date());
        logsService.save(logs);
    }


}
