package com.hzgc.manage.dao;

import com.hzgc.manage.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 用户Repository
 * created by liang on 2018/11/16
 */
public interface UserRepository extends ElasticsearchRepository<User, String> {

    /**
     * 按照姓名模糊匹配
     */
    Page<User> findByUsernameLike(String username, Pageable pageable);

    /**
     * 按照姓名、密码匹配登录
     */
    User findByUsernameAndPassword(String username, String password);

    /**
     * 按照姓名匹配
     */
    List<User> findByUsername(String username);
}
