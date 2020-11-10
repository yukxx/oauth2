package yukx.security.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yukx.security.service.interceptor.GlobalInterceptor;
import yukx.security.service.utils.RedisUtils;

/**
 * @ClassName WebMvcConfig
 * @Description web配置
 * @Author yukx
 * @Date 2020-10-30 10:46
 **/
//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        GlobalInterceptor interceptor = new GlobalInterceptor();
        interceptor.setRedisUtils(redisUtils);
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }
}
