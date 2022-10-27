package com.lendea.java_common_mistakes.controller;

import com.lendea.java_common_mistakes.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  * @Transactional生效原则
 *  * 1：除非特殊配置（比如使用 AspectJ 静态织入实现 AOP），否则只有定义在 public 方法上的 @Transactional 才能生效。原因是，Spring 默认通过动态代理的方式实现 AOP，对目标方法进行增强，private 方法无法代理到，Spring 自然也无法动态增强事务处理逻辑
 *  * 2：必须通过代理过的类从外部调用目标方法才能生效
 *  * <p>
 *  * CGLIB 通过继承方式实现代理类，private 方法在子类不可见，自然也就无法进行事务增强；
 *  * this 指针代表对象自己，Spring 不可能注入 this，所以通过 this 访问方法必然不是代理。
 *  * 默认情况下，出现 RuntimeException（非受检异常）或 Error 的时候，Spring 才会回滚事务。  在注解中声明(rollbackFor = Exception.class)，期望遇到所有的 Exception 都回滚事务（来突破默认不回滚受检异常的限制）
 *  * 只有异常传播出了标记了 @Transactional 注解的方法，事务才能回滚。要是自己吞了异常不会回滚，需要自己手动回滚
 *  *
 *  * 如果方法涉及多次数据库操作，并希望将它们作为独立的事务进行提交或回滚，那么我们需要考虑进一步细化配置事务传播方式，也就是 @Transactional 注解的 Propagation 属性。
 * @author lendea
 * @date 2022/8/12 15:01
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class TransactionController {

    @Autowired
    private UserService userService;

    @GetMapping("/wrong1")
    public int wrong(@RequestParam("name") String name) {
        return userService.createUserWrong(name);
    }

    @GetMapping("/right")
    public int right(@RequestParam("name") String name) {
        return userService.createUserRight(name);
    }

    @GetMapping("/right2")
    public int right2(@RequestParam("name") String name) {
        return userService.createUserPublic(name);
    }

    @GetMapping("/createUserAndSubUser")
    public int createUserAndSubUser(@RequestParam("name") String name) {
        return userService.createUserAndSubUser(name);
    }

}
