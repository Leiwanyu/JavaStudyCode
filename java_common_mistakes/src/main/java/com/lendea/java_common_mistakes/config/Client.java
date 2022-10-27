package com.lendea.java_common_mistakes.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author lendea
 * @date 2022/8/11 16:41
 */
@FeignClient(name = "clientsdk")
public interface Client {

    @PostMapping("/feignandribbon/server")
    String server();
}

