package com.hzgc.manage.controller;

import com.hzgc.manage.dto.*;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.User;
import com.hzgc.manage.enums.UserStatusEnums;
import com.hzgc.manage.service.UserService;
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

import javax.validation.Valid;
import java.util.List;

/**
 * 用户web层
 * created by liang on 18-11-20
 */
@RestController
@Api(value = "/user", tags = "用户服务")
@RequestMapping("/user")
public class UserController {

    private static String USER_CONTROLLER_CLASS_NAME = "com.hzgc.manage.controller.UserController";

    @Autowired
    private UserService userService;

    @ApiOperation(value = "账号登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResultVO<String> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        userService.login(userLoginDto, AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "login"));
        return ResultUtils.success();
    }

    @ApiOperation(value = "查询账号列表")
    @RequestMapping(value = "totalList", method = RequestMethod.GET)
    public ResultVO<List<User>> totalList(@RequestParam("userid") @ApiParam(name="userid",value="登录账号id",required=true) String userid) {

      Log log = new Log(userid, AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "totalList"));
        List<User> list = userService.findTotalByUserName(null, log);
        return ResultUtils.success(list);

    }

    @ApiOperation(value = "查询账号分页列表")
    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    public ResultVO<Page> pageList(@RequestBody UserQueryDto userQueryDto){

        Log log = new Log(userQueryDto.getUserId(), AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "pageList"));
            Pageable pageable =  PageRequest.of(userQueryDto.getPage(), userQueryDto.getSize());
            Page<User> page = userService.findPageByUserName(userQueryDto.getUsername(), pageable, log);
        return ResultUtils.success(page);
    }

    @ApiOperation(value = "新增账号")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResultVO<String> insert(@RequestBody @Valid UserUpdateDto userUpdateDto) {

        Log log = new Log(userUpdateDto.getUserId(), AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "insert"));
                 userService.insert(userUpdateDto,log);
        return ResultUtils.success();
    }

    @ApiOperation(value = "查询单一账号")
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public ResultVO<User> info( @ApiParam(name="userid",value="登录账号id",required=true) String userid,
                                @ApiParam(name="id",value="账号id",required=true) String id) {

        Log log = new Log(userid, AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "info"));
        User user = userService.findById(new UserDto(userid, id), log);
        return ResultUtils.success(user);
    }


    @ApiOperation(value = "修改账号")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResultVO<String> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        Log log = new Log(userUpdateDto.getUserId(), AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "update"));

        userService.update(userUpdateDto, log);
        return ResultUtils.success();
    }

    @ApiOperation(value = "删除账号")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public ResultVO<String> delete(@RequestBody @Valid UserDto userDto) {
        Log log = new Log(userDto.getUserId(), AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "delete"));
        userService.deleteById(userDto, log);
        return ResultUtils.success();
    }

    @ApiOperation(value = "修改账号密码")
    @RequestMapping(value = "editorpwd", method = RequestMethod.POST)
    public ResultVO<String> editorpwd(@RequestBody @Valid UserPwdDto userPwdDto) {
        Log log = new Log(userPwdDto.getUserId(), AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "editorpwd"));

        userService.editorpwd(userPwdDto, log);
        return ResultUtils.success();
    }

    @ApiOperation(value = "重置账号密码")
    @RequestMapping(value = "resetpwd", method = RequestMethod.POST)
    public ResultVO<String> resetpwd(@RequestBody @Valid UserDto userDto) {
        Log log = new Log(userDto.getUserId(), AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "resetpwd"));

        userService.resetpwd(userDto, log);
        return ResultUtils.success();
    }

    @ApiOperation(value = "禁用账号")
    @RequestMapping(value = "disable", method = RequestMethod.POST)
    public ResultVO<String> disable(@RequestBody @Valid UserDto userDto) {
        Log log = new Log(userDto.getUserId(), AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "disable"));

        userService.changeStatus(userDto, UserStatusEnums.DISABLE_USER_STATUS.getCode(), log);
        return ResultUtils.success();
    }

    @ApiOperation(value = "启用账号")
    @RequestMapping(value = "enable", method = RequestMethod.POST)
    public ResultVO<String> enable(@RequestBody UserDto userDto) {
        Log log = new Log(userDto.getUserId(), AnnUtils.getApiValue(USER_CONTROLLER_CLASS_NAME, "enable"));
        userService.changeStatus(userDto, UserStatusEnums.ENABLE_USER_STATUS.getCode(), log);
        return ResultUtils.success();
    }


}

