package com.lendea.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lendea
 * @date 2022/8/17 17:14
 */
public class ClassicDemo {

    public static void main(String[] args) {
        ClassicDemo classicDemo = new ClassicDemo();
        classicDemo.strategyUse1();

        classicDemo.strategyUse2("2");
        classicDemo.strategyUse3("2");
        classicDemo.strategyUse4("2");
    }


    public void strategyUse1() {
        Strategy strategy = new Concrete1Strategy();
        strategy.algorithmInterface();
    }

    public void strategyUse2(String type) {
        final StrategyFactory strategyFactory = new StrategyFactory();
        final Strategy strategy = strategyFactory.getStrategy(type);
        strategy.algorithmInterface();
    }

    public void strategyUse3(String type) {
        final StrategyFactory2 strategyFactory2 = new StrategyFactory2();
        final Strategy strategy = strategyFactory2.getStrategy(type);
        strategy.algorithmInterface();
    }

    public void strategyUse4(String type) {
        final StrategyFactory3 strategyFactory3 = new StrategyFactory3();
        final Strategy strategy = strategyFactory3.getStrategy(type);
        strategy.algorithmInterface();
    }
}


interface Strategy {
    void algorithmInterface();
}

class Concrete1Strategy implements Strategy {
    @Override
    public void algorithmInterface() {
        System.out.println("strategy1");
    }
}

class Concrete2Strategy implements Strategy {
    @Override
    public void algorithmInterface() {
        System.out.println("strategy2");
    }
}

class StrategyFactory {
    public static final Map<String, Strategy> strategies = new HashMap<>();

    static {
        strategies.put("1", new Concrete1Strategy());
        strategies.put("2", new Concrete2Strategy());
    }

    public Strategy getStrategy(String typeName) {
        if (typeName == null || typeName.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty");
        }
        return strategies.get(typeName);
    }
}

class StrategyFactory2 {
    public Strategy getStrategy(String typeName) {
        if (typeName == null || typeName.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty");
        }
        if ("1".equals(typeName)) {
            return new Concrete1Strategy();
        }
        if ("2".equals(typeName)) {
            return new Concrete2Strategy();
        }
        return null;
    }
}

class StrategyFactory3 {

    public static final Map<String, String> strategys = new HashMap<>();

    static {
        strategys.put("1", "com.lendea.strategy.Concrete1Strategy");
        strategys.put("2", "com.lendea.strategy.Concrete2Strategy");
    }

    public Strategy getStrategy(String typeName) {
        if (typeName == null || typeName.isEmpty()) {
            throw new IllegalArgumentException(("type should not be empty"));
        }
        try {
            return (Strategy) Class.forName(strategys.get(typeName)).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}