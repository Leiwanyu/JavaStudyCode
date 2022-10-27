package com.lendea.java_common_mistakes.service;

import com.lendea.java_common_mistakes.entity.User;
import com.lendea.java_common_mistakes.repostiory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author lendea
 * @date 2022/8/12 14:48
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService self;
    @Autowired
    private SubUserService subUserService;

    public int createUserWrong(String name) {
        try {
            this.createUserPrivate(new User(name));
        } catch (Exception e) {
            log.error("create user failed because {}", e.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    //    @标记了@Transactional的private方法
    @Transactional
    public void createUserPrivate(User user) {
        userRepository.save(user);
        if (user.getName().contains("test")) {
            throw new IllegalArgumentException("invalid username!!");
        }
    }

    public int createUserRight(String name) {
        try {
            self.createUserPrivate(new User(name));
        } catch (Exception e) {
            log.error("create user failed because {}", e.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    @Transactional
    public int createUserPublic(String name) {
        userRepository.save(new User(name));
        if ("test".equals(name)) {
            throw new IllegalArgumentException("invalid username!");
        }
        return userRepository.findByName(name).size();
    }

    @Transactional
    public int createUserPublic2(String name) {
        try {
            userRepository.save(new User(name));
            if ("test".equals(name)) {
                throw new IllegalArgumentException("invalid username!");
            }
        } catch (Exception e) {
            log.error("create user failed because {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return userRepository.findByName(name).size();
    }

    @Transactional
    public int createUserAndSubUser(String name) {
        userRepository.save(new User(name));
        try {
            subUserService.createSubUser(String.format("%s_sub", name));
        } catch (Exception e) {
            log.error("create user failed because {}", e.getMessage());
        }
        return getUserCount(name);
    }

    /**
     * 根据用户名称查询用户数
     *
     * @param name
     * @return
     */
    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }
}
