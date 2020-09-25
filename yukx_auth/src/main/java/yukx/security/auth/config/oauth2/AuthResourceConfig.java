package yukx.security.auth.config.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import yukx.security.auth.enums.OauthResourceEnum;

/**
 * @ClassName AuthResourceConfig
 * @Description 配置资源服务器
 * @Author yukx
 * @Date 2020-09-16 15:49
 **/
@Configuration
@EnableResourceServer
public class AuthResourceConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(OauthResourceEnum.RESOURCE1.resource).stateless(true);
    }

    // 参考：https://www.jianshu.com/p/fe1194ca8ecd
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/order/**").authenticated();*/
        http.authorizeRequests()
                .antMatchers("/**/test.*", "/**/*.css", "/**/*.js", "/**/images/*").permitAll()
                // admin 角色访问权限
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                // user 角色访问权限
                .antMatchers("/user/**").hasAuthority("ROLE_USER")
                // 公共页面
                .antMatchers("/pub/**").permitAll()
                // 其余所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                // login 页面自定义配置都可以访问
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and().logout().permitAll();    //任何人都可以登出页面都可以访问
    }
}
