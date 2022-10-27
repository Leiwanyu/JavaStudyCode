package com.lendea.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lendea
 * @date 2022/8/17 17:52
 */
public class UserDemo {
}


class UserCache {

    public static final Map<String, String> CACHE_MAP = new HashMap<>();
    private String evictionType;
    private Integer evictionType2;

    public UserCache(String evictionType,Integer evictionType2) {
        this.evictionType = evictionType;
        this.evictionType2 = evictionType2;
    }

    public void cache(String name, String user) {
        CACHE_MAP.put(name, user);
        evictionCache();
    }

    private void evictionCache() {
        if ("1".equals(evictionType)) {
            // TODO: 2022/8/17 lru
        } else if ("2".equals(evictionType)) {
            // TODO: 2022/8/17 fifo
        } else if ("3".equals(evictionType)) {
            // TODO: 2022/8/17 lfu
        }
        //......
    }

    private void evictCache() {
        new EvictionStrategyFactory().getEvictionStrategy(this.evictionType2).evictionCache();
    }

}

interface EvictionStrategy {
    void evictionCache();
}

class LruEvictionStrategy implements EvictionStrategy {
    @Override
    public void evictionCache() {
        System.out.println("LruEvictionStrategy");
    }
}

class FifoEvictionStrategy implements EvictionStrategy {

    @Override
    public void evictionCache() {
        System.out.println("FifoEvictionStrategy");
    }
}

class LfuEvictionStrategy implements EvictionStrategy {

    @Override
    public void evictionCache() {
        System.out.println("LfuEvictionStrategy");
    }
}

class EvictionStrategyFactory {
    public static final Map<Integer, EvictionStrategy> STRATEGY_MAP = new HashMap<>();

    static {
        STRATEGY_MAP.put(1, new LruEvictionStrategy());
        STRATEGY_MAP.put(2, new FifoEvictionStrategy());
        STRATEGY_MAP.put(3, new LfuEvictionStrategy());
    }

    public EvictionStrategy getEvictionStrategy(int type) {
        if (type == 0) {
            throw new IllegalArgumentException("illegal eviction strategy type!");
        }
        return STRATEGY_MAP.get(type);
    }

}