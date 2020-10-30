package yukx.security.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yukx.security.service.interceptor.GlobalInterceptor;

/**
 * @ClassName WebMvcConfig
 * @Description TODO
 * @Author yukx
 * @Date 2020-10-30 10:46
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        GlobalInterceptor interceptor = new GlobalInterceptor();
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }
}
