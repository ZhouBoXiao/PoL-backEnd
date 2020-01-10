package com.whu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;

//@WebFilter(filterName="ChannelFilter",urlPatterns= {"/PoL/admin/*"})
//@Order(1)
public class ChannelFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletRequest;
        HttpServletRequest request = (HttpServletRequest) servletResponse;
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        System.out.println(request.getHeader("Origin"));
        response.addHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.addHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", "x-requested-with");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
