package com.lendea.java_common_mistakes.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

/**
 * @author lendea
 * @date 2022/8/10 13:49
 */
@Slf4j
public class ThreadTest {
    volatile int a = 0, b = 0;
    public static final Object lock = new Object();

    /**
     * 两个线程，一个写，一个比较
     * <p>
     * 加锁前要清楚锁和被锁的对象是不是一个层面的。
     * 注意锁粒度，
     */
    @Test
    public void test() {
        new Thread(() -> {
            log.info("start add");
            synchronized (lock) {
                IntStream.rangeClosed(1, 10000).forEach(
                        i -> {
                            a++;
                            b++;
                        }
                );
            }
            log.info("end add");
        }
        ).start();

        new Thread(() -> {
            log.info("start compare");
            synchronized (lock) {
                IntStream.rangeClosed(1, 1000).forEach(
                        i -> {
                            if (a > b) {
                                log.error(String.format("a > b, a: %d, b: %d", a, b));
                            }
                        }
                );
            }
            log.info("end compare");
        }).start();
    }


}
