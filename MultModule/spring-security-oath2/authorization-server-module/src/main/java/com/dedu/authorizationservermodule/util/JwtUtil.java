package com.dedu.authorizationservermodule.util;

import com.dedu.authorizationservermodule.model.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    public static ThreadLocal<ImmutablePair<Key, Key>> KeyImmutablePair = new ThreadLocal<>();

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     * 
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public static SysUser parseTokenToSysUser(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(KeyImmutablePair.get().getRight())
                    .parseClaimsJws(token)
                    .getBody();

            SysUser user = SysUser.builder().id(Long.parseLong((String) body.get("userId"))).username(body.getSubject()).roles((String) body.get("role")).build();
            return user;
        } catch (JwtException e) {
            return null;
        }
    }

    public static Claims parseTokenToClaims(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(KeyImmutablePair.get().getRight())
                    .parseClaimsJws(token)
                    .getBody();

            return body;
        } catch (JwtException e) {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     * 
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public static String generateToken(SysUser u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getId() + "");
        claims.put("role", u.getRoles());

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
//                .setExpiration()
                .signWith(SignatureAlgorithm.HS512, KeyImmutablePair.get().getLeft())
                .compact();
    }

    public static boolean validateToken(String token) {
        Claims claims = parseTokenToClaims(token);
        if (null != claims) {
            claims.getExpiration().before(new Date());
        }
        return false;
    }
}