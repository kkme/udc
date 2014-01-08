package com.koudai.udc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;

public class P3PHearderFilter implements Filter, InitializingBean {

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		((HttpServletResponse) servletResponse).addHeader("P3P", "CP=CAO PSA OUR");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {
	}

	public void afterPropertiesSet() throws Exception {
	}

}
