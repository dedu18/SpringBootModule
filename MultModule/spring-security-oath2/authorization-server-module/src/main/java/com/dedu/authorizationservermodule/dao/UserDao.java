package com.dedu.authorizationservermodule.dao;

import com.dedu.authorizationservermodule.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserDao {

//    @Autowired
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private List<SysUser> systemUsers = new ArrayList<>();

    public SysUser findByUsername(String username) {
        if (systemUsers.isEmpty()) {
            systemUsers.add(new SysUser("dedu", passwordEncoder.encode("dedu"), "ADMIN,USER"));
            systemUsers.add(new SysUser("xiaoming", passwordEncoder.encode("xiaoming"), "USER"));
        }
        return systemUsers.stream().filter(t -> Objects.equals(t.getUsername(), username)).findFirst().get();
    }
}
