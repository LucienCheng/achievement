package com.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//获取请求的URL  
        String url = request.getRequestURI();  
       System.out.println(url);
       //登录的就是不会拦截
        if(url.indexOf("/back/login")>=0||"/achievement/back".equals(url)||url.indexOf("/front")>=0||
        		url.indexOf("/source")>=0||url.indexOf("/file")>=0||url.indexOf("/ueditor")>=0){  
            return true;  
        }  
        //获取Session  
        HttpSession session = request.getSession();  
        Integer roleId = (Integer)session.getAttribute("roleId");  
        
        Map<Integer, String> map=new HashMap<Integer, String>();
        map.put(1, "/back/user");
        map.put(2, "/back/auditor");
        map.put(3, "/back/admin");
        if(roleId == null){  
        	response.sendRedirect("/achievement/back");
        }  
        else {
        	if(url.indexOf(map.get(roleId))>=0){
        		return true;
        	}
        	else {
        		response.sendRedirect("/achievement/back");
			}
		}
     
        return false;  
	}

}
