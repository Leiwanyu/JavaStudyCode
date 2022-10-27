package com.lendea.java_common_mistakes;


import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

/**
 * 测试arraylist 和copyonwriteList 读100000数据和写一万数据的性能。
 *
 * @author lendea
 * @date 2022/8/10 11:25
 */

public class ArrayListTest {

    @Test
    public void testWrite() {
        final List<Long> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        final CopyOnWriteArrayList<Long> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Write:synchronizedList");
        LongStream.rangeClosed(1, 100000).parallel().forEach(v -> synchronizedList.add(ThreadLocalRandom.current().nextLong(v)));
        stopWatch.stop();

        stopWatch.start("Write:copyOnWriteArrayList");
        LongStream.rangeClosed(1, 100000).parallel().forEach(v -> copyOnWriteArrayList.add(ThreadLocalRandom.current().nextLong(v)));
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeSeconds());
    }

    @Test
    public void testRead() {

        final List<Long> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        final CopyOnWriteArrayList<Long> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        LongStream.rangeClosed(1, 1000000).parallel().forEach(v -> synchronizedList.add(ThreadLocalRandom.current().nextLong(v)));
        LongStream.rangeClosed(1, 1000000).parallel().forEach(v -> copyOnWriteArrayList.add(ThreadLocalRandom.current().nextLong(v)));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Read:synchronizedList");
        LongStream.rangeClosed(1, 1000000).parallel().forEach(v -> synchronizedList.get(ThreadLocalRandom.current().nextInt((int) v)));
        stopWatch.stop();

        stopWatch.start("Read:copyOnWriteArrayList");
        LongStream.rangeClosed(1, 1000000).parallel().forEach(v -> copyOnWriteArrayList.get(ThreadLocalRandom.current().nextInt((int) v)));
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeSeconds());
    }
}
