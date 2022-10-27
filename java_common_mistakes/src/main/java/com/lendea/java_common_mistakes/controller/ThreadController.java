package com.lendea.java_common_mistakes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * https://stackoverflow.com/questions/19528304/how-to-get-the-threadpoolexecutor-to-increase-threads-to-max-before-queueing
 * https://github.com/apache/tomcat/blob/a801409b37294c3f3dd5590453fb9580d7e33af2/java/org/apache/tomcat/util/threads/ThreadPoolExecutor.java
 * @author lendea
 * @date 2022/8/10 15:16
 */
@Slf4j
@RestController
@RequestMapping("/thread")
public class ThreadController {

    @GetMapping("/fixedThreadPoolOom")
    public String fixedThreadPoolOom() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);

        printStats((ThreadPoolExecutor) executorService);
        for (int i = 0; i < 100000000; i++) {
            executorService.execute(() -> {
                String payload = IntStream.rangeClosed(1, 100000).mapToObj(__ -> "i")
                        .collect(Collectors.joining(",")) + UUID.randomUUID().toString();
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.info("payload: {}", payload);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        return "创建一个固定线程池，执行一亿个长耗时任务";
    }

    @GetMapping("/cachedThreadPoolOom")
    public String cachedThreadPoolOom() throws InterruptedException {
        final ExecutorService executorService = Executors.newCachedThreadPool();

        printStats((ThreadPoolExecutor) executorService);
        for (int i = 0; i < 100000000; i++) {
            executorService.execute(() -> {
                String payload = IntStream.rangeClosed(1, 100000).mapToObj(__ -> "i")
                        .collect(Collectors.joining(",")) + UUID.randomUUID().toString();
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.info("payload: {}", payload);
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        return "创建一个cached线程池，执行一亿个长耗时任务";
    }


    @RequestMapping("/customThreadPool")
    public String customThreadPool() throws InterruptedException {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10),
                new ThreadFactory() {
                    final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
                    final AtomicInteger threadNumber = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = this.defaultFactory.newThread(r);
                        if (!thread.isDaemon()) {
                            thread.setDaemon(true);
                        }

                        thread.setName("customThread-" + this.threadNumber.getAndIncrement());
                        return thread;
                    }
                }, new ThreadPoolExecutor.AbortPolicy());
        printStats(threadPoolExecutor);

        final AtomicInteger atomicInteger = new AtomicInteger(0);
        IntStream.rangeClosed(1, 20).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int id = atomicInteger.incrementAndGet();
            try {
                threadPoolExecutor.submit(() -> {
                    log.info("{} started", id);

                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("{} finished", id);
                });
            } catch (Exception e) {
                //提交出现异常的话，打印错误信息并为计数器减1
                log.error("error submitting task {}", id, e);
                atomicInteger.decrementAndGet();
            }

        });
        TimeUnit.SECONDS.sleep(10);

        return "自定义线程池执行任务: " + atomicInteger.intValue();
    }


    private void printStats(ThreadPoolExecutor threadPool) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("=========================");
            log.info("Pool Size: {}", threadPool.getPoolSize());
            log.info("Active Threads: {}", threadPool.getActiveCount());
            log.info("Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());
            log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());

            log.info("=========================");
        }, 0, 1, TimeUnit.SECONDS);
    }
}
