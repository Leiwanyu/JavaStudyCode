package com.lendea.java_common_mistakes.controller;

import com.lendea.java_common_mistakes.config.Client;
import com.lendea.java_common_mistakes.config.SmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author lendea
 * @date 2022/8/11 14:57
 */
@Slf4j
@RestController
@RequestMapping("/feignandribbon")
public class FeignAndRibbonController {

    @Autowired
    private Client cli;
    @Autowired
    private SmsClient smsClient;

    @GetMapping("/client")
    public String client() {
        long begin = System.currentTimeMillis();

        try {
            return cli.server();
        } catch (Exception e) {
            log.warn("执行耗时： {}ms,错误：{}", System.currentTimeMillis() - begin, e.getMessage());
        }
        return "";
    }


    @PostMapping("/server")
    public String server() throws InterruptedException {
        TimeUnit.MINUTES.sleep(10);
        return "sleep 10 minutes";
    }

    @GetMapping("/retry/client")
    public String retryClient() {
        long begin = System.currentTimeMillis();
        try {
            return smsClient.sendSmsWrong("10000000000", "I love me");
        } catch (Exception e) {
            log.warn("执行耗时： {}ms,错误：{}", System.currentTimeMillis() - begin, e.getMessage());
        }
        return "success";
    }

    @GetMapping("/retry/server")
    public String retryServer(HttpServletRequest request, @RequestParam("mobile") String mobile, @RequestParam("message") String message) throws InterruptedException {
        log.info("{} is called, {}=>{}", request.getRequestURL().toString(), mobile, message);
        TimeUnit.SECONDS.sleep(2);
        return "success";
    }

}
