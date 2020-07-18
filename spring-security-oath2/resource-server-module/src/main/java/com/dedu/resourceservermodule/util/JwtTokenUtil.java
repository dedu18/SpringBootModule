package com.dedu.resourceservermodule.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public class JwtTokenUtil {

    private static final String AUTHENHEADERNAME = "";

    public static ThreadLocal<ImmutablePair<String, String>> KeyThreadLocal = new ThreadLocal<>();

    public static String getAuthentication(HttpServletRequest request) {
        String jwt = request.getHeader(AUTHENHEADERNAME);
        //PayLoad信息
        Claims claims;
        String subject = "";
        try {
            claims = Jwts.parser().setSigningKey(KeyThreadLocal.get().getLeft()).parseClaimsJws(jwt).getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
            claims = null;
        }
        return subject;
    }

    public static boolean verifyToken(String jwt, UserDetails userDetails) {
        if (StringUtils.isEmpty(jwt)) {
            return false;
        }

        return true;
    }
}
