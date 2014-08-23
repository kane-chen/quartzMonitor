package com.easeye.quartz.quartzmonitor.filter;

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

public class AuthenticationFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
		String url = httpServletRequest.getServletPath();
		
		System.out.println(url);
		
		HttpSession session = httpServletRequest.getSession();
		
		System.out.println("AuthenticationFilter: " + session);
		
		if (session.getAttribute("user") == null) {
			if (url.toLowerCase().endsWith("/login")) {
				filterChain.doFilter(request, response);
			} else if (url.toLowerCase().endsWith("/login.html")) {
				filterChain.doFilter(request, response);
			} else if (url.toLowerCase().endsWith("/authenticate_fail.html")) {
				filterChain.doFilter(request, response);
			} else if (url.toLowerCase().endsWith("/sync.action")) {
				filterChain.doFilter(request, response);
			}else {
				url = httpServletRequest.getContextPath() + "/login.html";
				((HttpServletResponse) response).sendRedirect(url);
				return;
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
