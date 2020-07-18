package com.dedu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login", "/authentication/form", "/global/*").permitAll() //自定义不进行认证的页面
                .antMatchers("/admin").access("@rbacPermission.hasPermission(request, authentication)") //使用自定义的表达式进行授权校验
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login") // 自定义登录页面
                .loginProcessingUrl("/authentication/form")// 自定义登录路径，即页面需要post的路径
                .successForwardUrl("/index") //配置登陆成功后的页面
                .failureUrl("/login")
                .and()
            .logout()
                .logoutSuccessUrl("/login") //配置退出后的页面
                .permitAll()
            .and()
            .csrf().disable();// 禁用跨站攻击
//        http.exceptionHandling().accessDeniedPage("/accessdenied"); //直接配置授权失败页面
        http.exceptionHandling().accessDeniedHandler(createAccessDeniedHandler()); //直接配置授权失败处理类
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//            .withUser("dedu").password(new BCryptPasswordEncoder().encode("dedu")).roles("ADMIN");
        //修改为使用自定义的认证信息
        auth.userDetailsService(sysUserService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //指定忽略的静态资源
        web.ignoring().antMatchers("/global/**");
    }

    /**
     * 必须显示定义加密编码器
     * @return
     */
    @Bean
    public BCryptPasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SysAccessDeniedHandler createAccessDeniedHandler() {
        return new SysAccessDeniedHandler();
    }
}
