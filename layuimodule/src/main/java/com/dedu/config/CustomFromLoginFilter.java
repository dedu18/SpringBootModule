//package com.dedu.config;
//
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CustomFromLoginFilter extends AbstractAuthenticationProcessingFilter {
//
//    public CustomFromLoginFilter(String defaultFilterProcessesUrl) {
//        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
//    	//读取页面穿过来的username和password
//        String username = httpServletRequest.getParameter("username");
//        String password = httpServletRequest.getParameter("password");
//        //校验账号密码是否通过
//        customCheck(username, password);
//        //授予USER权限
//        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
//        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("USER"));
//        return new UsernamePasswordAuthenticationToken(username, password, simpleGrantedAuthorities);
//    }
//
//    private void customCheck(String username, String password){
//    	//校验用户名为user密码为pass
//        if (!("user".equals(username) && "pass".equals(password))){
//            throw new RuntimeException("用户名或密码错误！");
//        }
//
//    }
//
//}