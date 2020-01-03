package com.sirius.robots.web.filter;

import com.google.common.base.Splitter;
import com.sirius.robots.comm.util.LogUtil;
import com.sirius.robots.web.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 日志id过滤器
 *
 * @author lilang
 */
@Slf4j
public class LogIdFilter implements Filter {

    private List<String> prefixIignores ;

    private String ignoresParam;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ignoresParam = filterConfig.getInitParameter("exclusions");
        if (StringUtils.isNotEmpty(ignoresParam)) {
            prefixIignores = Splitter.on(",").splitToList(ignoresParam);
        }
        return;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LogUtil.updateLogId(null);
        if (canIgnore(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = ((HttpServletRequest) servletRequest).getServletPath();
        log.info("验证用户,servletPath:{}",servletPath);
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("userInfo");
        if(Objects.isNull(userInfo)){
            request.getRequestDispatcher("/login").forward(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        prefixIignores=null;
    }

    private boolean canIgnore(ServletRequest request) {
        String servletPath = ((HttpServletRequest) request).getServletPath();
        return !PathUtil.commProcess(servletPath, prefixIignores, null);
    }
}

