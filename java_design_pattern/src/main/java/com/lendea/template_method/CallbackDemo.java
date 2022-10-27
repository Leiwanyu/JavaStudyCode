package com.lendea.template_method;

import java.util.HashMap;
import java.util.IdentityHashMap;

/**
 * 同步回调函数
 * @author lendea
 * @date 2022/8/17 15:03
 */
public class CallbackDemo {

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                System.out.println("shutdown hook")
        ));
    }

    public static void main(String[] args) {
        BClass bClass = new BClass();
        bClass.method2(new ICallBack() {
            @Override
            public void callback() {
                System.out.println("callback");
            }
        });

    }
}

class BClass{

    public void method2(ICallBack callBack) {
        // TODO: 2022/8/17 logic1
        callBack.callback();
        // TODO: 2022/8/17 logic2
    }
}

interface ICallBack{
    void callback();
}
