package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;


/**
 * @author Maplerain
 * @date 2023/4/16 2:31
 **/
@Slf4j
//@WebFilter(filterName = "ipCheckFilter",urlPatterns = "/*")
public class IpCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("IP check filter 执行了...");
    }
}
