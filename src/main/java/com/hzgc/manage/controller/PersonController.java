package com.hzgc.manage.controller;

import com.hzgc.jniface.BigPictureData;
import com.hzgc.bean.SearchOption;
import com.hzgc.manage.dto.PersonDto;
import com.hzgc.manage.dto.PersonQueryDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.Person;
import com.hzgc.manage.service.PersonService;
import com.hzgc.manage.vo.ResultVO;
import com.hzgc.utils.AnnUtils;
import com.hzgc.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.directory.SearchResult;

/**
 * 人口库服务web层
 * created by liang on 18-11-20
 */
@RestController
@Api(value = "/person", tags = "人口库服务")
@RequestMapping("/person")
public class PersonController {

    private static String PERSON_CONTROLLER_CLASS_NAME = "com.hzgc.manage.controller.PersonController";

    @Autowired
    private PersonService personService;


    @ApiOperation(value = "人口分页列表")
    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    public ResultVO<Page> pageList(@RequestBody PersonQueryDto personQueryDto){

        Log log = new Log(personQueryDto.getUserId(), AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "pageList"));
        Pageable pageable = PageRequest.of(personQueryDto.getPage(), personQueryDto.getSize());
        long l = System.currentTimeMillis();
        Page<Person> page = personService.findPageByXmSfz(personQueryDto, pageable, log);
        long ll = System.currentTimeMillis();
        System.out.println("pagelist cost:"+(ll-l));
        return ResultUtils.success(page);
    }

    @ApiOperation(value = "新增人口")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResultVO<String> insert(@RequestBody PersonDto personDto) {
        Log log = new Log(personDto.getUserId(), AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "insert"));
        long l = System.currentTimeMillis();
        personService.insert(personDto, log);
        long ll = System.currentTimeMillis();
        System.out.println("add cost:"+(ll-l));
        return ResultUtils.success();
    }

    @ApiOperation(value = "查询人口详情")
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public ResultVO<Person> info(@ApiParam(name="userid",value="登录账号id",required=true) String userid,
                                 @ApiParam(name="id",value="人口id",required=true) String id) {
        Log log = new Log(userid, AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "info"));
        long l = System.currentTimeMillis();
        Person person = personService.findById(id, log);
        long ll = System.currentTimeMillis();
        System.out.println("info cost:"+(ll-l));
        return ResultUtils.success(person);
    }


    @ApiOperation(value = "修改人口")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResultVO<String> update(@RequestBody PersonDto personDto) {
        Log log = new Log(personDto.getUserId(), AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "insert"));
        personService.update(personDto);
        return ResultUtils.success();
    }

    @ApiOperation(value = "delete请求",notes="根据人口ID删除")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public ResultVO<String> delete(@ApiParam(name="userid",value="登录账号id",required=true) String userid,
                                   @ApiParam(name="id",value="人口id",required=true) String id) {
        Log log = new Log(userid, AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "delete"));
        long l = System.currentTimeMillis();
        personService.deleteById(id);
        long ll = System.currentTimeMillis();
        System.out.println("delete cost:"+(ll-l));
        return ResultUtils.success();
    }

    @ApiOperation(value = "人脸特征值提取", response = BigPictureData.class)
    @RequestMapping(value = "/extract_picture", method = RequestMethod.POST)
    public ResultVO<BigPictureData> faceFeatureExtract(@ApiParam(name = "image", value = "图片") MultipartFile image,
                                                       @ApiParam(name="userid",value="登录账号id",required=true) String userid) {

        Log log = new Log(userid, AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "delete"));

        byte[] imageBin = null;
        if (image == null) {
//            log.error("Start extract feature by binary, image is null");
//            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT);
        }
//        try {
//            imageBin = image.getBytes();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //邏輯
//       // BigPictureData bigPictureData = faceExtractService.featureExtractByImage(imageBin);
//        if (null == bigPictureData) {
//            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT, "提取不到特征值");
//        }
        BigPictureData bigPictureData = new BigPictureData();
        return ResultUtils.success(bigPictureData);
    }

    @ApiOperation(value = "以图搜图", response = SearchResult.class)
    @RequestMapping(value = "/search_picture", method = RequestMethod.POST)
    public ResultVO<SearchResult> searchPicture(
            @RequestBody @ApiParam(value = "以图搜图查询参数") SearchOption searchOption,
            @ApiParam(name="userid",value="登录账号id",required=true) String userid) {

        Log log = new Log(userid, AnnUtils.getApiValue(PERSON_CONTROLLER_CLASS_NAME, "delete"));

        SearchResult searchResult;
        if (searchOption == null) {
//            log.error("Start search picture, but search option is null");
//            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT);
        }

        if (searchOption.getImages() == null) {
//            log.error("Start search picture, but images is null");
//            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT);
        }

        if (searchOption.getSimilarity() < 0.0) {
//            log.error("Start search picture, but threshold is error");
//            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT);
        }
//        Map<String, Device> ipcMapping = DeviceToIpcs.getIpcMapping(searchOption.getDeviceIpcs());
//        searchOption.setIpcMapping(ipcMapping);
//        if (searchOption.getDeviceIpcs() == null
//                || searchOption.getDeviceIpcs().size() <= 0
//                || searchOption.getDeviceIpcs().get(0) == null) {
//            log.error("Start search picture, but deviceIpcs option is error");
//            return ResponseResult.error(RestErrorCode.ILLEGAL_ARGUMENT);
//        }
//        log.info("Start search picture, set search id");
//        String searchId = UuidUtil.getUuid();
//        log.info("Start search picture, search option is:" + JacksonUtil.toJson(searchOption));
//        searchResult = captureSearchService.searchPicture2(searchOption, searchId);
//        return ResponseResult.init(searchResult);


        return  new ResultVO<SearchResult>();
    }
}
