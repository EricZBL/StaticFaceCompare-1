package com.hzgc.manage.controller;

import cn.hutool.core.bean.BeanUtil;
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

import javax.validation.Valid;

/**
 * 日志服务web层
 * created by liang on 18-11-20
 */
@RestController
@Api(value = "/log", tags = "日志服务")
@RequestMapping("/log")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @ApiOperation(value = "查询日志分页列表")
    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    public ResultVO<com.hzgc.utils.Page> pageList(@RequestBody @Valid LogQueryDto logQueryDto){

            Pageable pageable = PageRequest.of(logQueryDto.getPage() - 1, logQueryDto.getSize());
            Page<Log> page = logService.findPage(logQueryDto, pageable);

            com.hzgc.utils.Page<Log> logPage = new com.hzgc.utils.Page<>();
            BeanUtil.copyProperties(page, logPage);
            logPage.setNumber(page.getNumber() + 1);
            logPage.setTotalElements(page.getTotalElements());
            logPage.setSize(page.getSize());

        return ResultUtils.success(logPage);
    }

}

