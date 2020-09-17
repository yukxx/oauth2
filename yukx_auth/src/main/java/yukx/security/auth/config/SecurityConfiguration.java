package yukx.security.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    /*@Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
        return manager;
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user_1")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .authorities("USER")
                .and()
                .withUser("user_2")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .authorities("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/*").permitAll();
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
