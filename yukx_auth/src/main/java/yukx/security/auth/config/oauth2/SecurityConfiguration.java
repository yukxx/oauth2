package yukx.security.auth.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @ClassName SecurityConfiguration
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-16 16:12
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    /*@Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
        return manager;
    }*/

    /**
     * 现实{@link UserDetailsService} 接口，从数据库读取用户信息
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 基于内存方式读取用户信息
     *
     * @throws Exception
     */
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user_1")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .authorities("USER")
                .and()
                .withUser("user_2")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .authorities("USER");
    }*/

    // 该配置会被 ResourceServerConfigurerAdapter 覆盖
    // 解释参考：https://www.jianshu.com/p/fe1194ca8ecd
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/**/test.*", "/**/*.css", "/**/*.js", "/**/images/*").permitAll()
//                // admin 角色访问权限
//                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//                // user 角色访问权限
//                .antMatchers("/user/**").hasAuthority("ROLE_USER")
//                // 公共页面
//                .antMatchers("/pub/**").permitAll()
//                // 其余所有请求全部需要鉴权认证
//                .anyRequest().authenticated()
//                .and()
//                // login 页面自定义配置都可以访问
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and().logout().permitAll();    //任何人都可以登出页面都可以访问
//    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略静态资源
        web.ignoring().antMatchers("/resources/**");
    }

    /**
     * 使用spring默认的认证Manager
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
