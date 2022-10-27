package com.lendea.java_common_mistakes.controller;

import cn.hutool.core.util.RuntimeUtil;
import com.lendea.java_common_mistakes.utils.AccessCountUtils;
import com.lendea.java_common_mistakes.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
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

    @PostConstruct
    public void postConstruct() {
        System.out.println("============="+"PostConstruct");
    }

    @PreDestroy
    public void destory() {
        System.out.println("============="+"destory");
    }

    public void initPostConstruct() {
        System.out.println("post contr");
    }

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

    @GetMapping
    public void hello() throws FileNotFoundException {
        RuntimeUtil.exec(null, new File("src/main/resources/shell/"), "chmod +x hello.sh");
        final Process process = RuntimeUtil.exec(null, new File("src/main/resources/shell/"), "sh hello.sh ");
        final List<String> resultLines = RuntimeUtil.getResultLines(process);
        resultLines.forEach(System.out::println);
    }


}
