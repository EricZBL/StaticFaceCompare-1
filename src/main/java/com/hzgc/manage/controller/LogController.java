package com.hzgc.manage.controller;

import com.hzgc.manage.dto.LogQueryDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.service.LogService;
import com.hzgc.manage.vo.ResultVO;
import com.hzgc.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志服务web层
 * created by liang on 18-11-20
 */
@RestController
@Api(value = "/log", tags = "日志服务")
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @ApiOperation(value = "查询日志分页列表")
    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    public ResultVO<Page> pageList(@RequestBody LogQueryDto logQueryDto){

            Pageable pageable = PageRequest.of(logQueryDto.getPage(), logQueryDto.getSize());
            Page<Log> page = logService.findPage(logQueryDto, pageable);

        return ResultUtils.success(page);
    }

}

