package com.lendea.java_common_mistakes.thread;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 场景：下订单时扣减库存
 * 下订单时获取所有商品库存锁
 * 扣减所有商品库存
 * 释放锁
 *
 * 为什么会有死锁发送，
 * 假设 a,b商品。
 * a用户加锁顺序是 a, b；
 * b用户加锁顺序是 b, a;
 * a，b并发下单时候，会发送死锁。
 *
 * 怎么解决
 * 顺序加锁，将商品排序
 * 都按照 a,b加锁
 *
 * @author lendea
 * @date 2022/8/10 14:16
 */
@Slf4j
public class DeadThreadTest {

    private static ConcurrentHashMap<String, Item> items = new ConcurrentHashMap<>();


    /**
     * 放入商品
     */
    @BeforeAll
    public static void pre() {
        IntStream.range(0, 10).forEach(i -> items.put("item" + i, new Item("item" + i)));
    }

    @Test
    public void testWrong() {
        wrong();
    }

    @Test
    public void testRight() {
        right();
    }

    @Data
    @RequiredArgsConstructor
    static class Item{
        private final String name;//商品名称
        private Integer remaining = 1000;//库存剩余
        @ToString.Exclude //toString不包含该字段
        ReentrantLock lock = new ReentrantLock();
    }


    //每次从购物车下单三件商品
    private List<Item> createCart() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> "item" + ThreadLocalRandom.current().nextInt(items.size()))
                .map(name -> items.get(name))
                .collect(Collectors.toList());
    }


    private boolean createOrder(List<Item> order) {
        //存放所有获得的锁
        List<ReentrantLock> locks = new ArrayList<>();

        for (Item item : order) {
            try {
                //获得锁10秒超时
                if (item.lock.tryLock(10, TimeUnit.SECONDS)) {
                    locks.add(item.lock);
                } else {
                    locks.forEach(ReentrantLock::unlock);
                    return false;
                }
            } catch (InterruptedException e) {
            }
        }
        //锁全部拿到之后执行扣减库存业务逻辑
        try {
            order.forEach(item -> item.remaining--);
        } finally {
            locks.forEach(ReentrantLock::unlock);
        }
        return true;
    }


    public long wrong() {
        long begin = System.currentTimeMillis();
        //并发进行100次下单操作，统计成功次数
        long success = IntStream.rangeClosed(1, 100).parallel()
                .mapToObj(i -> {
                    List<Item> cart = createCart();
                    return createOrder(cart);
                })
                .filter(result -> result)
                .count();
        log.info("success:{} totalRemaining:{} took:{}ms items:{}",
                success,
                items.values().stream().map(item -> item.remaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin, items);
        return success;
    }

    public long right() {
        long begin = System.currentTimeMillis();
        //并发进行100次下单，统计成功次数，下单时候对订单商品进行排序

        final long count = IntStream.rangeClosed(1, 100).parallel()
                .mapToObj(i -> {
                    final List<Item> items = createCart()
                            .stream()
                            .sorted(Comparator.comparing(Item::getName))
                            .collect(Collectors.toList());
                    return createOrder(items);
                })
                .filter(result -> result).count();
        log.info("success:{},\n totalRemaining:{}\n,took:{}ms,items:{}",count,
                items.values().stream().map(item -> item.remaining).reduce(0,Integer::sum),
                System.currentTimeMillis()-begin,items);
        return count;
    }


}


