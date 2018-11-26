package com.hzgc.manage.service.impl;


import com.hzgc.manage.dto.UserDto;
import com.hzgc.manage.dto.UserPwdDto;
import com.hzgc.manage.dto.UserUpdateDto;
import com.hzgc.manage.entity.Log;
import com.hzgc.manage.service.LogService;
import org.apache.commons.lang.StringUtils;
import com.hzgc.exception.HzgcException;
import com.hzgc.manage.content.GlobalCont;
import com.hzgc.manage.dao.UserRepository;
import com.hzgc.manage.dto.UserLoginDto;
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
    public void insert(UserUpdateDto userUpdateDto, Log log) {

        String username = userUpdateDto.getUsername();
        if (StringUtils.isBlank(username)) throw new HzgcException(ExceptionCodeEnums.USERNAME_ISNOT_BLANK);
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setOriginpwd(GlobalCont.DEFALUT_USER_PASSWORD);
        user.setStatus(UserStatusEnums.ENABLE_USER_STATUS.getCode());
        user.setCreatetime(new Date());
        user.setPassword(MD5Utills.formPassToDBPass(GlobalCont.DEFALUT_USER_PASSWORD, GlobalCont.DEFALUT_USER_SALT));

        userRepository.save(user);

    }

    @Override
    public Page<User> findPageByUserName(String userName, Pageable pageable, Log log) {
        logService.save(log);
        if(StringUtils.isNotBlank(userName)){
        return userRepository.findByUsernameLike(userName, pageable);
        }
        return this.findPageAll(pageable);
    }

    @Override
    public Page<User> findPageAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    public User findById(UserDto userDto, Log log) {
        logService.save(log);
        return this.selectOneById(userDto.getId());

    }

    @Override
    public void deleteById(UserDto userDto, Log log) {
        String id = userDto.getId();
        if (StringUtils.isNotBlank(id))  throw new HzgcException(ExceptionCodeEnums.USERID_ISNOT_BLANK);
        userRepository.deleteById(id);
        logService.save(log);
    }

    @Override
    @Transactional
    public void resetpwd(UserDto userDto, Log log) {
        String id = userDto.getId();
        User user = this.selectOneById(id);
            user.setPassword(MD5Utills.formPassToDBPass(GlobalCont.DEFALUT_USER_PASSWORD, GlobalCont.DEFALUT_USER_SALT));
        this.save(user);
        logService.save(log);
    }

    @Override
    @Transactional
    public void changeStatus(UserDto userDto, Integer status, Log log) {
            User user = this.selectOneById(userDto.getId());
            user.setStatus(status);
        this.save(user);
        logService.save(log);
    }

    @Override
    @Transactional
    public void update(UserUpdateDto userDto, Log log) {
        User user = this.selectOneById(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setStatus(userDto.getStatus());
        this.save(user);
        logService.save(log);
    }

    @Override
    public void editorpwd(UserPwdDto userPwdDto, Log log) {
        User user = this.selectOneById(userPwdDto.getId());
        user.setPassword(userPwdDto.getNewPassword());
        this.save(user);
        logService.save(log);
    }

    @Override
    public void login(UserLoginDto userLoginDto,  String logname) {

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
    }

    @Override
    public List<User> findTotalByUserName(String username, Log log) {

            if(StringUtils.isNotBlank(username)){
               return  userRepository.findByUsername(username);
            }
            Iterable<User> userIterable = userRepository.findAll();
             List<User> list = new ArrayList<>();
            userIterable.forEach(single ->list.add(single));
        insertLog(log);
        return list;
    }

    private User selectOneById(String id){
        if (StringUtils.isNotBlank(id))  throw new HzgcException(ExceptionCodeEnums.USERID_ISNOT_BLANK);
        return userRepository.findById(id).get();
    }

    private void insertLog(Log log){
        log.setId(UUID.randomUUID().toString());
        User user = this.selectOneById(log.getUserid());
        log.setUsername(user.getUsername());
        log.setCreatetime(new Date());
        logService.save(log);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
