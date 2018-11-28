package com.hzgc.manage.service.impl;


import cn.hutool.core.util.IdUtil;
import com.hzgc.manage.dto.*;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.service.LogService;
import org.apache.commons.lang.StringUtils;
import com.hzgc.exception.HzgcException;
import com.hzgc.manage.content.GlobalCont;
import com.hzgc.manage.dao.UserRepository;
import com.hzgc.manage.entity.User;
import com.hzgc.manage.enums.ExceptionCodeEnums;
import com.hzgc.manage.enums.UserStatusEnums;
import com.hzgc.manage.service.UserService;
import com.hzgc.utils.MD5Utills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.Date;
import java.util.ArrayList;

/**
 * 用户服务层实现
 * created by liang on 18-11-16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogService logService;

    @Override
    @Transactional
    public void insert(UserCreateDto userCreateDto, Log log) {
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setStatus(UserStatusEnums.ENABLE_USER_STATUS.getCode());
        this.save(user);
        this.insertLog(log);

    }

    @Override
    public Page<User> findPageByUserName(String userName, Pageable pageable, Log log) {
        //this.insertLog(log);
        if(StringUtils.isNotBlank(userName)){
        return userRepository.findByUsernameLike("*" + userName + "*", pageable);
        }
        return this.findPageAll(pageable);

    }

    @Override
    public Page<User> findPageAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    public User findById(UserDto userDto, Log log) {
        this.insertLog(log);
        return this.selectOneById(userDto.getId());

    }

    @Override
    public void deleteById(UserDto userDto, Log log) {
        String id = userDto.getId();

        if (StringUtils.isBlank(id))  throw new HzgcException(ExceptionCodeEnums.USERID_ISNOT_BLANK);

        User user = this.selectOneById(id);
        if(user.getUsername().equals(GlobalCont.DEFALUT_USER_ADMIIN))  throw new HzgcException(ExceptionCodeEnums.USER_ADMIN_ISNOT_OPERATRE);

        userRepository.deleteById(id);
        this.insertLog(log);
    }

    @Override
    @Transactional
    public void resetpwd(UserDto userDto, Log log) {
        String id = userDto.getId();
        User user = this.selectOneById(id);
        if(user.getUsername().equals(GlobalCont.DEFALUT_USER_ADMIIN))  throw new HzgcException(ExceptionCodeEnums.USER_ADMIN_ISNOT_OPERATRE);

        user.setPassword(MD5Utills.formPassToDBPass(GlobalCont.DEFALUT_USER_PASSWORD, GlobalCont.DEFALUT_USER_SALT));
        user.setOriginpwd(GlobalCont.DEFALUT_USER_PASSWORD);
        userRepository.save(user);
        this.insertLog(log);
    }

    @Override
    @Transactional
    public void changeStatus(UserDto userDto, Integer status, Log log) {
        User user = this.selectOneById(userDto.getId());
        if(user.getUsername().equals(GlobalCont.DEFALUT_USER_ADMIIN))  throw new HzgcException(ExceptionCodeEnums.USER_ADMIN_ISNOT_OPERATRE);
        user.setStatus(status);
        userRepository.save(user);
        this.insertLog(log);
    }

    @Override
    @Transactional
    public void update(UserUpdateDto userDto, Log log) {

        String username = this.selectOneById(userDto.getId()).getUsername();

        if(username.equals(GlobalCont.DEFALUT_USER_ADMIIN))  throw new HzgcException(ExceptionCodeEnums.USER_ADMIN_ISNOT_OPERATRE);

        userRepository.deleteById(userDto.getId());

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setStatus(userDto.getStatus());
        this.save(user);
        this.insertLog(log);
    }


    @Override
    public void editorpwd(UserPwdDto userPwdDto, Log log) {
        User user = this.selectOneById(userPwdDto.getId());
        if(! user.getOriginpwd().equals(userPwdDto.getOriginpwd()))   throw new HzgcException(ExceptionCodeEnums.USERNAME_ISNOT_MATCH);

        if(user.getUsername().equals(GlobalCont.DEFALUT_USER_ADMIIN))  throw new HzgcException(ExceptionCodeEnums.USER_ADMIN_ISNOT_OPERATRE);

        user.setOriginpwd(userPwdDto.getNewPassword());
        user.setPassword(MD5Utills.formPassToDBPass(userPwdDto.getNewPassword(), GlobalCont.DEFALUT_USER_SALT));
        userRepository.save(user);
        this.insertLog(log);
    }

    @Override
    public User login(UserLoginDto userLoginDto, String logname) {

        User user = userRepository.findByUsernameAndPassword(userLoginDto.getUsername(), MD5Utills.formPassToDBPass(userLoginDto.getPassword(), GlobalCont.DEFALUT_USER_SALT));

        if(user == null)  throw new HzgcException(ExceptionCodeEnums.USERPWD_ISNOT_MATCH);

        if(user.getStatus() == UserStatusEnums.DISABLE_USER_STATUS.getCode())  throw new HzgcException(ExceptionCodeEnums.USER_IS_DISABLE);

        if(user != null && (user.getStatus() == UserStatusEnums.ENABLE_USER_STATUS.getCode())){
            Log log = new Log();
            log.setId(user.getId());
            log.setLogname(logname);
            log.setId(UUID.randomUUID().toString());
            log.setUsername(user.getUsername());
            log.setCreatetime(new Date());
            logService.save(log);
        }
        return user;
    }

    @Override
    public List<User> findTotalByUserName(String username, Log log) {

            if(StringUtils.isNotBlank(username)){
               return  userRepository.findByUsername(username);
            }
            Iterable<User> userIterable = userRepository.findAll();
             List<User> list = new ArrayList<>();
            userIterable.forEach(single ->list.add(single));
        //this.insertLog(log);
        return list;
    }

    private User selectOneById(String id){
        if (StringUtils.isBlank(id))  throw new HzgcException(ExceptionCodeEnums.USERID_ISNOT_BLANK);
        return userRepository.findById(id).get();
    }

    private void insertLog(Log log){
        log.setId(IdUtil.simpleUUID());
        User user = this.selectOneById(log.getUserid());
        log.setUsername(user.getUsername());
        log.setCreatetime(new Date());
        logService.save(log);
    }

    @Override
    public void save(User user) {
        String username = user.getUsername();
        List<User> userList = userRepository.findByUsername(username);
        if(userList != null && userList.size() > 0)  throw new HzgcException(ExceptionCodeEnums.USER_IS_EXIST);

        if (StringUtils.isBlank(username)) throw new HzgcException(ExceptionCodeEnums.USERNAME_ISNOT_BLANK);
        user.setId(IdUtil.simpleUUID());
        user.setOriginpwd(GlobalCont.DEFALUT_USER_PASSWORD);
        user.setCreatetime(new Date());
        user.setPassword(MD5Utills.formPassToDBPass(GlobalCont.DEFALUT_USER_PASSWORD, GlobalCont.DEFALUT_USER_SALT));

        userRepository.save(user);
    }
}
