package com.hyron.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DailyEnglishLoginFilter implements Filter{

	@Override
	public void destroy() {
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try{
			HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession session = req.getSession(true);

            String server = extractServerInfo(request, req);
            String username = (String) session.getAttribute("username");
            String url = req.getRequestURL().toString();
            if(url.contains("/pages/")){
            	if(username == null || "".equalsIgnoreCase(username)){
                	resp.sendRedirect(server + "/include/login.html");
                	return;
                }else{
                	chain.doFilter(request, response);
                }
            }else{
            	return;
            } 
            chain.doFilter(request, response);
		}catch(Exception e){
			throw new ServletException(e.getMessage());
		}
	}

	protected String extractServerInfo(ServletRequest request, HttpServletRequest req) {
        String servername = req.getServerName();
        String contextPath = req.getContextPath();
        int port = request.getServerPort();

        String protocol = "http";
        if (request.isSecure()) {
            protocol = "https";
        }
        String server = protocol + "://" + servername + ":" + port + contextPath;
        return server;
    }

}
