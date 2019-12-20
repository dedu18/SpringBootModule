package com.dedu.resourceservermodule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.KeyPair;
import java.util.Base64;

/**
 * 配置此服务为资源服务器
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .antMatchers("/api/**").hasAuthority("USER");
//    }

    //**************配置JWT解析

    private static final String RESOURCEID = "resources";

    private static final String AESSECRET = "123456";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(createDefaultTokenServices()).resourceId(RESOURCEID);
    }

    private DefaultTokenServices createDefaultTokenServices() throws Exception {
        DefaultTokenServices dts = new DefaultTokenServices();
        dts.setTokenStore(createTokenStore());
        return dts;
    }

    private TokenStore createTokenStore() throws Exception {
        return new JwtTokenStore(createJwtTokenConverter());
    }

//    /**
//     * 基于AES对称加密的JWT
//     * @return
//     */
//    @Bean
//    public JwtAccessTokenConverter createJwtTokenConverter() {
//        JwtAccessTokenConverter jwtTokenCvter = new JwtAccessTokenConverter();
//        // 基于AES对称加密，需提供一个加密Key
//        jwtTokenCvter.setSigningKey(AESSECRET);
//        return jwtTokenCvter;
//    }

    /**
     *
     * 基于RSA非对称加密的JWT
     * @return
     */
    @Bean
    public JwtAccessTokenConverter createJwtTokenConverter() throws Exception {
        JwtAccessTokenConverter jwtTokenCvter = new JwtAccessTokenConverter();
        KeyPair keyPair = null; // 需要配置
        String pub = new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
        String pri = new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
        System.out.println("Public:" + pub);
        System.out.println("Private:" + pri);
//        JwtUtil.KeyImmutablePair.set(new ImmutablePair<>(keyPair.getPublic(), keyPair.getPrivate()));
        jwtTokenCvter.setKeyPair(keyPair);
        return jwtTokenCvter;
    }

}
