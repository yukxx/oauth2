package yukx.security.auth.config.oauth2;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import yukx.security.auth.enums.OauthClientEnum;
import yukx.security.auth.enums.OauthResourceEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AuthorizationServerConfig
 * @Description 配置授权服务
 * @Author yukx
 * @Date 2020-09-14 17:56
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    AuthenticationManager authenticationManager;        // oauth2内置对象

    @Autowired
    RedisConnectionFactory redisConnectionFactory;


    /**
     * 配置AuthorizationServer安全认证的相关信息，创建ClientCredentialsTokenEndpointFilter核心过滤器
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    /**
     * 配置OAuth2的客户端相关信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 属性说明参考：https://andaily.com/spring-oauth-server/db_table_description.html

        clients.inMemory().withClient(OauthClientEnum.CLIENT1.clientId)
                .resourceIds(OauthResourceEnum.RESOURCE1.resource)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("select")
                .authorities("client")
                .secret("{bcrypt}" + new BCryptPasswordEncoder().encode(OauthClientEnum.CLIENT1.secret))
                .and()
                .withClient(OauthClientEnum.CLIENT2.clientId)
                .resourceIds(OauthResourceEnum.RESOURCE1.resource)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("server")
                .authorities("client")
                // 需要添加前缀原因：org.springframework.security.crypto.password.DelegatingPasswordEncoder.matches`
                .secret("{bcrypt}" + new BCryptPasswordEncoder().encode(OauthClientEnum.CLIENT1.secret));
    }

    /**
     * 配置AuthorizationServerEndpointsConfigurer众多相关类，包括配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 采用redis
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                // jwtToken
                .accessTokenConverter(accessTokenConverter())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .reuseRefreshTokens(true);

    }

    /**
     * jwt
     *
     * @return {@link JwtAccessTokenConverter}
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /**
             * 重写增强token方法，用于自定义一些token总需要封装的信息
             * 使用jwt，其实就是在默认token上封装成jwt
             * 此方法在/oauth/token 会执行（第一次会执行，后面存redis了）
             * {@link DefaultTokenServices#createAccessToken(OAuth2Authentication, OAuth2RefreshToken)}
             * 此方法 accessTokenEnhancer 变量,该变量默认为null
             * 来自接口{@link TokenEnhancer#enhance(org.springframework.security.oauth2.common.OAuth2AccessToken, org.springframework.security.oauth2.provider.OAuth2Authentication)}
             * @param accessToken
             * @param authentication
             * @return
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String userName = authentication.getUserAuthentication().getName();
                // 得到用户名， 去处理数据库可以拿到当前用户信息和角色信息（需要传递到服务中用到的信息）
                final Map<String, Object> additionalInformation = new HashMap<>();
                // map假装用户实体
                Map<String, String> userinfo = new HashMap<>();
                userinfo.put("id", "1");
                userinfo.put("username", "yukx");
                userinfo.put("qqnum", "694883436");
                userinfo.put("userFlag", "1");
                additionalInformation.put("userinfo", JSON.toJSONString(userinfo));
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                return super.enhance(accessToken, authentication);
            }
        };
        // 测试用加密方式，资源服务器使用相同的字符达到对称加密效果
        accessTokenConverter.setSigningKey("SigningKey");
        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
