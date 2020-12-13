package com.yu.project09.interceptor;

import lombok.extern.java.Log;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log
public class LoginCheckInterceptor implements HandlerInterceptor { // HandlerInterceptorAdapter은 deprecated됨

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandler.....................");
        
        String dest = request.getParameter("dest");
        
        if(dest != null){ // 파라미터에 dest가 존재한다면 HttpSession에 저장함
            request.getSession().setAttribute("dest", dest);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
