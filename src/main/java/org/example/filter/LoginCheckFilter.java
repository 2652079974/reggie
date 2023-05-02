package org.example.filter;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseContext;
import org.example.common.Result;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

/**
 * @author Maplerain
 * @date 2023/4/6 0:09
 **/
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json;charset=UTF-8");

        String[] urls = {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        if (check(urls, request.getRequestURI())) {
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("employee") != null) {
            Long userId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }
        ;
        if (request.getSession().getAttribute("user") != null) {
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }

        response.getWriter().write(JSONObject.toJSONString(Result.error("NOTLOGIN")));
    }

    /**
     * 检查请求的 url 是否在 预定义的 urls 数组当中
     *
     * @param urls
     * @param requestURI
     * @return
     */
    private boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean result = PATH_MATCHER.match(url, requestURI);
            if (result) {
                return true;
            }
        }
        return false;
    }
}
