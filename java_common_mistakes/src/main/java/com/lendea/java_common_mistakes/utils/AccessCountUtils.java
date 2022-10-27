package com.lendea.java_common_mistakes.utils;

import org.springframework.util.StopWatch;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author lendea
 * @date 2022/8/10 10:52
 */
public class AccessCountUtils {

    public static final ConcurrentHashMap<String, LongAdder> pathAccessCountMap = new ConcurrentHashMap<>();


    public static void count(String key) {
        //利用computeIfAbsent()方法来实例化LongAdder，然后利用LongAdder来进行线程安全计数
        pathAccessCountMap.computeIfAbsent(key, k -> new LongAdder()).increment();
    }

    public static long getAccessCount(String key) {
        return pathAccessCountMap.get(key).longValue();
    }

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Thread.sleep(1000);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeSeconds());
    }

}
