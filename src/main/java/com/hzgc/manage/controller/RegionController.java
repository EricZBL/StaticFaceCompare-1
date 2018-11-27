package com.hzgc.manage.controller;

import com.hzgc.manage.dto.LogQueryDto;
import com.hzgc.manage.entity.Areas;
import com.hzgc.manage.entity.Cities;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.Provinces;
import com.hzgc.manage.service.LogService;
import com.hzgc.manage.service.RegionService;
import com.hzgc.manage.vo.ResultVO;
import com.hzgc.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 日志服务web层
 * created by liang on 18-11-20
 */
@RestController
@Api(value = "/region", tags = "区域服务")
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @ApiOperation(value = "查询省份列表")
    @RequestMapping(value = "getProvinces", method = RequestMethod.GET)
    public ResultVO<List<Provinces>> getProvinces(){
         List<Provinces> list = regionService.findProvince();
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "查询城市列表")
    @RequestMapping(value = "getCities", method = RequestMethod.GET)
    public ResultVO<List<Cities>> getCities(@RequestParam("provinceid") @ApiParam(name="provinceid",value="省份provinceid",required=true) String provinceid){
        List<Cities> list = regionService.findByProvinceid(provinceid);
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "查询地区列表")
    @RequestMapping(value = "getAreas", method = RequestMethod.GET)
    public ResultVO<List<Areas>> pageList(@RequestParam("cityid") @ApiParam(name="cityid",value="城市cityid",required=true) String cityid){
        List<Areas> list = regionService.findByCityid(cityid);
        return ResultUtils.success(list);
    }

}

