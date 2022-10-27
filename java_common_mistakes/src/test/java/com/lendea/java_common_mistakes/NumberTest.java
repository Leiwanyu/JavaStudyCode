package com.lendea.java_common_mistakes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author lendea
 * @date 2022/8/12 18:01
 */
public class NumberTest{

    @Test
    public void testDouble() {
        System.out.println(0.1 + 0.2);
        System.out.println(1.0 - 0.8);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);

        double amount1 = 2.15;
        double amount2 = 1.10;

        if (amount1 - amount2 == 1.05) {
            System.out.println("OK");
        }

        System.out.println(String.format("%.1f",amount1));//四舍五入

        final DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        System.out.println(decimalFormat.format(amount1));
    }

    /**
     * 使用Bigdecimal表示和计算浮点数，务必使用字符串的构造方法来初始化Bigdecimal
     */
    @Test
    public void testBigDecimal() {
        System.out.println(new BigDecimal(0.1).add(new BigDecimal(0.2)));
        System.out.println(new BigDecimal(1.0).subtract(new BigDecimal(0.8)));
        System.out.println(new BigDecimal(4.015).multiply(new BigDecimal(100)));
        System.out.println(new BigDecimal(123.3).divide(new BigDecimal(100)));

        System.out.println(new BigDecimal("0.1").add(new BigDecimal("0.2")));
        System.out.println(new BigDecimal("1.0").subtract(new BigDecimal("0.8")));
        System.out.println(new BigDecimal("4.015").multiply(new BigDecimal(100)));
        System.out.println(new BigDecimal("123.3").divide(new BigDecimal(100)));


        System.out.println(new BigDecimal("1.0"));
        System.out.println(new BigDecimal("1"));
        System.out.println(new BigDecimal("1.0").stripTrailingZeros());
        System.out.println(new BigDecimal("1.0").equals(new BigDecimal("1")));
        System.out.println(new BigDecimal("1.0").compareTo(new BigDecimal("1")) == 0);
    }

    @Test
    public void testOverflow() {
        long l = Long.MAX_VALUE;
        System.out.println(l);
        System.out.println(l + 1);
//        System.out.println(l + 1 == Long.MIN_VALUE);

        //使用math.addExact 在溢出时候会报错。
        System.out.println(Math.addExact(l, 1));

        final BigInteger bigInteger = new BigInteger(String.valueOf(Integer.MAX_VALUE));
        System.out.println(bigInteger.add(BigInteger.ONE).toString());
        final int i = bigInteger.add(BigInteger.ONE).intValueExact();
        System.out.println(i);
    }

}
