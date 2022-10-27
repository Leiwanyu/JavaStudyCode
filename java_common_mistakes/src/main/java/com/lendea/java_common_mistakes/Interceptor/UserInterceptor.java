package com.lendea.java_common_mistakes.Interceptor;

import com.lendea.java_common_mistakes.utils.AccessCountUtils;
import com.lendea.java_common_mistakes.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author lendea
 * @date 2022/8/9 14:50
 */
@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {


    /**
     * 在请求处理之前进行调用（controller之前进行调用）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        // 解析token，获取用户信息
        // 认证
        // ......
        String key = String.format("%s:%s",request.getMethod(),request.getRequestURL());
        AccessCountUtils.count(key);

        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（controller方法调用之后）
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    /**
     * 整个请求结束之后调用，DispatchServlet渲染了对应的视图之后执行（主要进行资源清理）
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //线程处理完成，清理线程资源
        UserUtils.clear();
    }
}
