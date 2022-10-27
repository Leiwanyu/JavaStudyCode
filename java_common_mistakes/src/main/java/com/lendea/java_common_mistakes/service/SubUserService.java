package com.lendea.java_common_mistakes.service;

import com.lendea.java_common_mistakes.entity.User;
import com.lendea.java_common_mistakes.repostiory.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lendea
 * @date 2022/8/12 16:02
 */
@Slf4j
@Service
public class SubUserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String createSubUser(String name) {
        userRepository.save(new User(name));
        if ("test_sub".equals(name)) {
            throw new IllegalArgumentException("illegal username!");
        }
        return "success";
    }
}
