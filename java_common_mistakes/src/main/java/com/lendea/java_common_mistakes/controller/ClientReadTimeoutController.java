package com.lendea.java_common_mistakes.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lendea
 * @date 2022/8/11 11:43
 */
@Slf4j
@RestController
@RequestMapping("/clientreadtimeout")
public class ClientReadTimeoutController {

    @GetMapping("/client")
    public String client() throws IOException {
        log.info("client1 called");
        return getResponse("/server?timeout=5000", 1000, 2000);
    }

    @GetMapping("/server")
    public void server(@RequestParam("timeout") int timeout) throws InterruptedException {
        log.info("server called");
        TimeUnit.MILLISECONDS.sleep(timeout);
        log.info("server called done");
    }

    private String getResponse(String url, int connectionTimeout, int readTimeout) throws IOException {
        return Request.Get("http://localhost:8080/clientreadtimeout" + url)
                .connectTimeout(connectionTimeout)
                .socketTimeout(readTimeout)
                .execute()
                .returnContent()
                .asString();
    }
}
