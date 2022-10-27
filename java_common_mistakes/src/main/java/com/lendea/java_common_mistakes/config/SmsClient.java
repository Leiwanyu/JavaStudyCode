package com.lendea.java_common_mistakes.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lendea
 * @date 2022/8/11 17:30
 */
@FeignClient("smsClient")
public interface SmsClient {

    @GetMapping("/feignandribbon/retry/server")
    String sendSmsWrong(@RequestParam("mobile") String mobile, @RequestParam("message") String message);
}
