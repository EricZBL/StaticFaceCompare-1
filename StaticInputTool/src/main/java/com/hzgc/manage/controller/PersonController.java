package com.hzgc.manage.controller;


import com.hzgc.manage.service.PersonService;
import com.hzgc.utils.ResultUtils;
import com.hzgc.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * 用户web层
 * created by liang on 18-11-20
 */
@RestController
@Api(value = "/person", tags = "用户服务")
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @ApiOperation(value = "新增人口")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultVO insert(@RequestBody String dirPath) {
        long l = System.currentTimeMillis();
        try {
            personService.insert(dirPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long ll = System.currentTimeMillis();
        System.out.println("add cost:" + (ll - l));
        return ResultUtils.success();
    }



    @ApiOperation(value = "新增人口")
    @RequestMapping(value = "/importdata", method = RequestMethod.POST)
    public ResultVO importdata(@RequestBody String province) {
            personService.importdata(province);
        return ResultUtils.success();

}

}

