package com.dedu.dao;

import com.dedu.model.SysUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserDao {

    private List<SysUser> sysUserList = new ArrayList<>(2);

    {
        sysUserList.add(new SysUser(1L, "dedu", new BCryptPasswordEncoder().encode("dedu"), "ROLE_USER, ROLE_ADMIN"));
        sysUserList.add(new SysUser(2L, "xiaoming", new BCryptPasswordEncoder().encode("xiaoming"), ""));
    }

    public SysUser findByUsername(String username) {
        SysUser sysUser = sysUserList.stream().filter(t-> Objects.equals(t.getUsername(), username)).findFirst().get();
        return sysUser;
    }
}
