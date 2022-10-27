package com.lendea.chain;

/**
 * @author lendea
 * @date 2022/8/18 09:42
 */
public class ChainDemo1 {
    public static void main(String[] args) {
        System.out.println("chain design pattern!");
        HandlerChain handlerChain = new HandlerChain();
        handlerChain.addHandler(new ConcreteHandler1());
        handlerChain.addHandler(new ConcreteHandler2());
        handlerChain.handle();
    }
}

abstract class Handler {
    protected Handler sucessor;

    abstract void handle();

    public void setSuccessor(Handler handler) {
        this.sucessor = handler;
    }
}

class ConcreteHandler1 extends Handler {

    @Override
    void handle() {
        boolean handled = false;
        System.out.println("ConcreteHandler1");
        // TODO: 2022/8/18  logical
        if (!handled && this.sucessor != null) {
            this.sucessor.handle();
        }
    }
}

class ConcreteHandler2 extends Handler {

    @Override
    void handle() {
        boolean handled = false;
        System.out.println("concreteHandler2");
        // TODO: 2022/8/18 logical
        handled = true;
        if (!handled && this.sucessor != null) {
            this.sucessor.handle();
        }
    }
}

class HandlerChain {
    private Handler head = null;
    private Handler tail = null;

    public void addHandler(Handler handler) {
        handler.setSuccessor(null);
        if (head == null) {
            head = handler;
            tail = handler;
            return;
        }
        tail.setSuccessor(handler);
        tail = handler;
    }

    public void handle() {
        if (head != null) {
            head.handle();
        }
    }
}

