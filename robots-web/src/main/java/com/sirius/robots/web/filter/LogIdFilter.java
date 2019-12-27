package com.sirius.robots.web.filter;

import com.sirius.robots.comm.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * 日志id过滤器
 *
 * @author lilang
 */
@Slf4j
public class LogIdFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LogUtil.updateLogId(null);
    }

    @Override
    public void destroy() {

    }
}
