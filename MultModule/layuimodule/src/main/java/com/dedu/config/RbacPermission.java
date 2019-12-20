package com.dedu.config;

import com.dedu.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Component("rbacPermission")
public class RbacPermission {

    @Autowired
    private UserDao userDao;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            // 读取用户所拥有的权限url，这里应该从数据库获取
            Set<String> urls = new HashSet<>();
            urls.add("/admin");

            for (String url : urls) {
                // 判断当前url是否有权限
                if(antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}