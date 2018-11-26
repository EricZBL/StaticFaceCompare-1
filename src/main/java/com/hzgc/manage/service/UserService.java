package com.hzgc.manage.service;

import com.hzgc.manage.dto.UserDto;
import com.hzgc.manage.dto.UserUpdateDto;
import com.hzgc.manage.dto.UserLoginDto;
import com.hzgc.manage.dto.UserPwdDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户服务层接口
 * created by liang on 2018/11/16
 */
public interface UserService {

    /**
     * 保存User实体
     */
    void save(User user);

    /**
     * 基于姓名（userName）进行搜索，返回分页
     */
    Page<User> findPageByUserName(String userName, Pageable pageable, Log log);

    /**
     * 返回所有数据，返回分页
     */
    Page<User> findPageAll(Pageable pageable);

    /**
     * 根据ID查询单一对象
     */
    User findById(UserDto userDto, Log log);

    /**
     * 新增User实体
     */
    void insert(UserUpdateDto userUpdateDto, Log log);

    /**
     * 根据ID删除User实体
     */
    void deleteById(UserDto userDto, Log log);

    /**
     * 根据ID重置密码User
     */
    void resetpwd(UserDto userDto, Log log);

    /**
     * 根据ID, 改变用户的状态
     */
    void changeStatus(UserDto userDto, Integer status, Log log);

    /**
     * 根据ID, 修改用户
     */
    void update(UserUpdateDto userDto, Log log);

    /**
     * 根据ID修改密码
     */
    void editorpwd(UserPwdDto userPwdDto, Log log);

    /**
     * 根据账号用户名、密码登录
     */
    void login(UserLoginDto userLoginDto, String logname);

    /**
     * 返回所有数据
     */
    List<User> findTotalByUserName(String username, Log log);
}
