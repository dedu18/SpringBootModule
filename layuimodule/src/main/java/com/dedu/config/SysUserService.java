package com.dedu.config;

import com.dedu.dao.UserDao;
import com.dedu.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (!StringUtils.isEmpty(user.getRoles())) {
            String[] roles = user.getRoles().split(",");
            for (String role : roles) {
                if (!StringUtils.isEmpty(role)) {
                    authorities.add(new SimpleGrantedAuthority(role.trim()));
                }
            }
        }
        //这里可直接让SysUser继承实现UserDetails接口，或者使用SpringSecurity提供的User类
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
