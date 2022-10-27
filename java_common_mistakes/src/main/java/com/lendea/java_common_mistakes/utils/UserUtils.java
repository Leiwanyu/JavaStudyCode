package com.lendea.java_common_mistakes.utils;

/**
 * 使用threadlocal时候，要注意要是使用的线程池，一旦线程重用。那么很可能取出上次使用这个线程储存的数据，造成数据混乱，因此每次线程处理完记得清理。
 *
 * @author lendea
 * @date 2022/8/9 15:38
 */
public class UserUtils {

    public static final ThreadLocal<String> LOGIN_USER = ThreadLocal.withInitial(() -> null);

    public static void setLoginUser(String userName) {
        LOGIN_USER.set(userName);
    }

    public static String getLoginUser() {
        final String loginName = LOGIN_USER.get();
        if (loginName == null) {
//            throw new IllegalArgumentException("当前登陆用户不存在");
        }
        return loginName;
    }

    public static void clear() {
        LOGIN_USER.remove();
    }
}
