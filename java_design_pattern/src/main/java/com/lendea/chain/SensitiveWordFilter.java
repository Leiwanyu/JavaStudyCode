package com.lendea.chain;

/**
 * @author lendea
 * @date 2022/8/18 10:38
 */
public class SensitiveWordFilter {

    public static void main(String[] args) {
        SensitiveWordFilter sensitiveWordFilter = new SensitiveWordFilter();
        final boolean b = sensitiveWordFilter.filter("dajflkjklj lijla");
        if (b) {
            System.out.println("可发布");
        } else {
            System.out.println("退回，不可发布");
        }
    }

    public boolean filter(String content) {
        if (filterSexyWord(content)) {
            // TODO: 2022/8/18
        }
        if (filterAdsWord(content)) {
            // TODO: 2022/8/18
        }
        if (filterPoliticalWord(content)) {
            // TODO: 2022/8/18
        }
        return true;
    }

    private boolean filterSexyWord(String content) {
        // TODO: 2022/8/18
        System.out.println("sexy word filter");
        return true;
    }

    private boolean filterAdsWord(String content) {
        // TODO: 2022/8/18
        System.out.println("ad word filter");
        return true;
    }

    private boolean filterPoliticalWord(String content) {
        // TODO: 2022/8/18
        System.out.println("political word filter");
        return true;
    }
}
