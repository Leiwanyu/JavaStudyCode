package com.lendea.java_common_mistakes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.lendea.java_common_mistakes.config")
@SpringBootApplication
public class JavaCommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaCommonMistakesApplication.class, args);
    }

}
