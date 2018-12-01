package com.hzgc.manage.controller;

import cn.hutool.core.util.IdUtil;
import com.hzgc.jniface.*;
import com.hzgc.manage.dto.PersonDto;
import com.hzgc.manage.dto.PersonQueryDto;
import com.hzgc.manage.dto.SearchDto;
import com.hzgc.manage.dto.UserDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.Person;
import com.hzgc.manage.service.PersonService;
import com.hzgc.manage.vo.ResultVO;
import com.hzgc.manage.vo.SingleSearchResult;
import com.hzgc.utils.AnnUtils;
import com.hzgc.utils.PageUtils;
import com.hzgc.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.directory.SearchResult;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * 人口库服务web层
 * created by liang on 18-11-20
 */
@RestController
@Api(value = "/person", tags = "人口库服务")
@RequestMapping("/person")
@Slf4j
public class PersonController {

    private static String PERSON_CONTROLLER_CLASS_NAME = "com.hzgc.manage.controller.PersonController";

    @Autowired
    private PersonService personService;


    @ApiOperation(value = "人口分页列表")
    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    public ResultVO<PageUtils> pageList(@RequestBody @Valid PersonQueryDto personQueryDto){

        Log log = new Log(personQueryDto.getUserId(), AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "pageList"));
        Pageable pageable = PageRequest.of(personQueryDto.getPage()-1, personQueryDto.getSize());
        PageUtils<Person> page = personService.findPageByXmSfz(personQueryDto, pageable, log);
        return ResultUtils.success(page);
    }

    @ApiOperation(value = "新增人口")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResultVO<String> insert(@RequestBody @Valid PersonDto personDto) {
        Log log = new Log(personDto.getUserId(), AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "insert"));
        personService.insert(personDto, log);
        return ResultUtils.success();
    }

    @ApiOperation(value = "查询人口详情")
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public ResultVO<Person> info(@RequestParam("userid") @ApiParam(name="userid",value="登录账号id",required=true) String userid,
                                 @RequestParam("id") @ApiParam(name="id",value="人口id",required=true) String id) {
        Log log = new Log(userid, AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "info"));
        Person person = personService.findById(id, log);
        return ResultUtils.success(person);
    }


    @ApiOperation(value = "修改人口")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResultVO<String> update(@RequestBody @Valid PersonDto personDto) {
        Log log = new Log(personDto.getUserId(), AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "update"));
        personService.update(personDto, log);
        return ResultUtils.success();
    }

    @ApiOperation(value = "删除人口")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public ResultVO<String> delete(@RequestBody @Valid UserDto userDto) {
        Log log = new Log(userDto.getUserId(), AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "delete"));
        personService.deleteById(userDto.getId(), log);
        return ResultUtils.success();
    }

    @ApiOperation(value = "人脸特征值提取", response = BigPictureData.class)
    @RequestMapping(value = "/extract_picture", method = RequestMethod.POST)
    public ResultVO<BigPictureData> faceFeatureExtract(@RequestParam("image") @ApiParam(name = "image", value = "图片") MultipartFile image,
                                                       @RequestParam("userId") @ApiParam(name="userId",value="登录账号id",required=true) String userId) {
        Log log = new Log(userId, AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "faceFeatureExtract"));
        BigPictureData bigPictureData = personService.featureExtractByImage(image, log);
        if (null == bigPictureData) {
            return null;
        }
        return ResultUtils.success(bigPictureData);
    }

    @ApiOperation(value = "以图搜图", response = SearchResult.class)
    @RequestMapping(value = "/search_picture", method = RequestMethod.POST)
    public ResultVO<SearchResult> searchPicture(
            @RequestBody @Valid SearchDto searchDto) {

        Log log = new Log(searchDto.getUserId(), AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "searchPicture"));

        SingleSearchResult singleSearchResult = personService.search_picture(searchDto);

        return  ResultUtils.success(singleSearchResult);
    }

    @ApiOperation(value = "获取原图片")
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@RequestParam("personid") @ApiParam(name="personid",value="personid",required=true) String personid) {
        byte[] image = personService.getImage(personid);
        if (image == null || image.length == 0) {
            return ResponseEntity.badRequest().contentType(MediaType.IMAGE_JPEG).body(null);
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
