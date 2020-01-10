package com.whu.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by tengj on 2017/3/29.
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag =true;
        String ip = request.getRemoteAddr();
        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        if(handler instanceof ResourceHttpRequestHandler) {
//            System.out.println("---------ResourceHttpRequestHandler-------" + handler.toString() + "------------");
        }else if(handler instanceof HandlerMethod){
//            System.out.println("--------HandlerMethod--------" + handler.toString() + "------------");
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            System.out.println("用户:"+ip+",访问目标:"+method.getDeclaringClass().getName() + "." + method.getName());
        }


//        User user=(User)request.getSession().getAttribute("user");
//        if(null==user){
//            response.sendRedirect("toLogin");
//            flag = false;
//        }else{
//            flag = true;
//        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(handler instanceof ResourceHttpRequestHandler) {
//            System.out.println("---------ResourceHttpRequestHandler-------" + handler.toString() + "------------");
//
//
//            long startTime = (Long) request.getAttribute("requestStartTime");
//            long endTime = System.currentTimeMillis();
//            long executeTime = endTime - startTime;
//            // 打印方法执行时间
//            if (executeTime > 1000) {
//                System.out.println("执行耗时 : "
//                        + executeTime + "ms");
//            } else {
//                System.out.println("执行耗时 : "
//                        + executeTime + "ms");
//            }
        }else if(handler instanceof HandlerMethod){
//            System.out.println("--------HandlerMethod--------" + handler.toString() + "------------");
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            long startTime = (Long) request.getAttribute("requestStartTime");
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
            // 打印方法执行时间
            if (executeTime > 1000) {
                System.out.println("[" + method.getDeclaringClass().getName() + "." + method.getName() + "] 执行耗时 : "
                        + executeTime + "ms");
            } else {
                System.out.println("[" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "] 执行耗时 : "
                        + executeTime + "ms");
            }
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Origin", "*");
            System.out.println(response.getHeader("Access-Control-Allow-Origin"));
//            System.out.println(request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods",
                    "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }



}
