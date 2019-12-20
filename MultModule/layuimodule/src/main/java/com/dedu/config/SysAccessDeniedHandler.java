package com.dedu.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    private String accessDeniedUrl = "/accessdenied";
  
    public SysAccessDeniedHandler() {
    }
  
    public SysAccessDeniedHandler(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }
  
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
       request.getSession().setAttribute("message", "You do not have permission to access this page!");
       response.sendRedirect(accessDeniedUrl);
    }
  
}