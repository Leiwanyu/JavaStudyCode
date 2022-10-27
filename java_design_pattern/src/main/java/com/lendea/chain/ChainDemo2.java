package com.lendea.chain;

/**
 * @author lendea
 * @date 2022/8/18 10:05
 */
public class ChainDemo2 {

}

abstract class Handler2{
    protected Handler2 successor;

    public void setSuccessor(Handler2 successor) {
        this.successor = successor;
    }

    protected void handle() {
        final boolean handled = doHandle();
        if (!handled && this.successor != null) {
            this.successor.handle();
        }
    }

    abstract boolean doHandle();
}

class ConcreteHandle1 extends Handler2{
    @Override
    boolean doHandle() {
        boolean handled = false;
        // TODO: 2022/8/18 logic
        return handled;
    }
}

class ConcreteHandle2 extends Handler2{
    @Override
    boolean doHandle() {
        boolean handled = false;
        // TODO: 2022/8/18 logic
        return handled;
    }
}

class HandlerChain2 {

    private Handler2 head;
    private Handler2 tail;

    public void addHandler(Handler2 handler2) {
        handler2.setSuccessor(null);
        if (head == null) {
            head = handler2;
            tail = handler2;
            return;
        }
        tail.setSuccessor(handler2);
        tail = handler2;
    }

    public void handle() {
        if (head != null) {
            head.handle();
        }
    }
}