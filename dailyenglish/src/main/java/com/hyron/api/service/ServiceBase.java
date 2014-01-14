package com.hyron.api.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;

public class ServiceBase {
    protected Object getBean(HttpServletRequest request, String beanName) {
        WebApplicationContext wax = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        return wax.getBean(beanName);
    }
    
    protected Response ok(Object obj){
        Gson gson = new Gson();
        String str = gson.toJson(obj);
        return Response.ok(str).build();
    }
}
