package com.dedu.authorizationservermodule.config;

import com.dedu.authorizationservermodule.util.JwtUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置内存存储Token令牌
 */
@Configuration
@EnableAuthorizationServer
public class MemoryAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final String clientId = "sysclientid";

    private final String secretId = "syssecretid";

    private final String authorizedGrantTypes = "authorization_code";

    private final String scopes = "applist";

    private final String redirectUri = "http://localhost:9001/hello";

    private static final String AESSECRET = "123456";

    /**
     * 配置Token令牌为JWT格式，则需要配置
     * 前面的 JdbcTokenStore 需要的是数据源，那现在的 jwt 需要的是什么呢？他需要一个叫做 令牌转换器 的东西，有了他我们才能够生成 jwt 格式的 token
     * @return
     */
    @Bean
    public TokenStore tokenStore() throws Exception {
        return new JwtTokenStore(createJwtTokenConverter());
    }

//        /**
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
        KeyPair keyPair = null;
        String pub = new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
        String pri = new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
        System.out.println("Public:" + pub);
        System.out.println("Private:" + pri);
        JwtUtil.KeyImmutablePair.set(new ImmutablePair<>(keyPair.getPublic(), keyPair.getPrivate()));
        jwtTokenCvter.setKeyPair(keyPair);
        return jwtTokenCvter;
    }

//    /**
//     * 未使用JWT增强
//     * @param endpoints
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints
//            .tokenStore(tokenStore())
//            .accessTokenConverter(createJwtTokenConverter());
//    }

    /**
     * 使用了JWT增强扩展信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), createJwtTokenConverter()));

        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain);
    }

    /**
     * 扩展JWT中的信息
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = new HashMap<>();
            authentication.getPrincipal();
            additionalInfo.put("email", "dedu.com");
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient(clientId)
            .secret(createPasswordEncoder().encode(secretId))
            .authorizedGrantTypes(authorizedGrantTypes)
            .scopes(scopes)  //申请的权限列表，会直接展示在申请页面上
            .redirectUris(redirectUri);
    }

    /**
     * 必须显示定义加密编码器
     * @return
     */
    @Bean
    public BCryptPasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
