package com.lendea.utils;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author lendea
 * @date 2022/9/5 10:03
 */
public class RandomTest {

    public static void main(String[] args) {
        long initialTime = RandomUtils.nextLong(3, 8) * 1000L;
        System.out.println(initialTime);

    }
}
