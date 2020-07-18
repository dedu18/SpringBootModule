package com.dedu.zuulmodule.config;

import com.dedu.zuulmodule.enums.FilterType;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 认证过滤器
 */
@Component
@Slf4j
public class AuthenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterType.PRE.getValue();
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String contextPath = request.getContextPath();
        String token = request.getParameter("token");
        log.info("ContextPath Is {}, Token Is {}", contextPath, token);
        if (StringUtils.isNotBlank(token)) {
            //正常执行后面请求
            return null;
        } else {
            try {
                // 转发至内部的异常处理
                currentContext.getResponse().sendRedirect("/tokenerror");
            } catch (IOException e) {
                log.error("Zuul SendRedirect Exception");
            }
        }
        return null;
    }
}
