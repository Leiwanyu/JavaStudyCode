package com.lendea.java_common_mistakes.controller;

import com.lendea.java_common_mistakes.utils.AccessCountUtils;
import com.lendea.java_common_mistakes.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author lendea
 * @date 2022/8/9 14:47
 */
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    @GetMapping("/{name}")
    public String testThreadLocal(@PathVariable String name) {
        String pre = UserUtils.getLoginUser();
        log.info(String.format("%s,pre: %s", Thread.currentThread().getName(), pre));
        UserUtils.setLoginUser(name);
        String post = UserUtils.getLoginUser();
        log.info(String.format("%s,post: %s", Thread.currentThread().getName(), post));
        return String.format("%s -> %s", pre, post);
    }


    @GetMapping("/accessCount")
    public Map<String, LongAdder> accessCount() {
        return AccessCountUtils.pathAccessCountMap;
    }


}
