package com.lendea.java_common_mistakes;


import cn.hutool.core.util.RuntimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public void testa() {
        RuntimeUtil.exec(null, new File("/Users/leiwanyu/studyworkspace/study_demo/"), "chmod +x a.sh");
        final Process process = RuntimeUtil.exec(null, new File("/Users/leiwanyu/studyworkspace/study_demo/"), "sh a.sh");
        final List<String> resultLines = RuntimeUtil.getResultLines(process);
        resultLines.forEach(System.out::println);
    }

    @Test
    public void testHash() {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algorithm not found.");
        }
        md5.reset();
        md5.update("192.168.1.1:8080".getBytes(StandardCharsets.UTF_8));
        byte[] bKey = md5.digest();
        long res = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8)
                | (long) (bKey[0] & 0xFF);
        System.out.println(res & 0xffffffffL);
    }

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
