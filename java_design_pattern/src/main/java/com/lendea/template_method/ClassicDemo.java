package com.lendea.template_method;

/**
 * 标准格式
 * @author lendea
 * @date 2022/8/17 11:27
 */
public class ClassicDemo {

    public static void main(String[] args) {
        AbstractClass class1 = new ContreteClass1();
        class1.templateMethod();
    }
}


abstract class AbstractClass{

    public final void templateMethod() {
        // TODO: 2022/8/17 logic
        method1();
        // TODO: 2022/8/17 logic
        method2();
    }

    protected abstract void method1();

    protected abstract void method2();
}

class ContreteClass1 extends AbstractClass {

    @Override
    protected void method1() {
        // TODO: 2022/8/17  logic
    }

    @Override
    protected void method2() {
        // TODO: 2022/8/17 logic
    }
}

class ContreteClass2 extends AbstractClass{

    @Override
    protected void method1() {
        // TODO: 2022/8/17 logic
    }

    @Override
    protected void method2() {
        // TODO: 2022/8/17 logic
    }
}