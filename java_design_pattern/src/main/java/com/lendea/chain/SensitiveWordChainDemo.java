package com.lendea.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * 应对代码复杂性：
 * 大块代码逻辑拆分成函数；
 * 大类拆分成小类；
 *
 * @author lendea
 * @date 2022/8/18 10:44
 */
public class SensitiveWordChainDemo {

    public static void main(String[] args) {
        SensitiveWordChain sensitiveWordChain = new SensitiveWordChain();
        sensitiveWordChain.addFilter(new SexyWordFilter());
        sensitiveWordChain.addFilter(new PoliticalWordFilter());
        sensitiveWordChain.addFilter(new AdsWordFilter());
        final boolean b = sensitiveWordChain.filter("adadx");
        if (b) {
            System.out.println("发布");
        } else {
            System.out.println("不合法，不发布");
        }
    }
}

interface SensitiveWordFilter2 {
    boolean doFilter();
}

class SexyWordFilter implements SensitiveWordFilter2 {

    @Override
    public boolean doFilter() {
        System.out.println("SexyWordFilter");
        return true;
    }
}

class PoliticalWordFilter implements SensitiveWordFilter2 {

    @Override
    public boolean doFilter() {
        System.out.println("PoliticalWordFilter");
        return true;
    }
}

class AdsWordFilter implements SensitiveWordFilter2 {

    @Override
    public boolean doFilter() {
        System.out.println("AdsWordFilter");
        return true;
    }
}

class SensitiveWordChain {

    private List<SensitiveWordFilter2> filters = new ArrayList<>();

    public void addFilter(SensitiveWordFilter2 filter2) {
        this.filters.add(filter2);
    }

    public boolean filter(String contents) {
        for (SensitiveWordFilter2 filter : filters) {
            if (!filter.doFilter()) {
                return false;
            }
        }
        return true;
    }

}