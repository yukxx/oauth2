package yukx.security.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @ClassName BeanConfig
 * @Description TODO
 * @Author yukx
 * @Date 2020-10-28 16:30
 **/
@Configuration
public class BeanConfig {

    /**
     * 配置redis
     *
     * @param factory
     * @return
     */
    @Bean(name = {"redisTemplate", "stringRedisTemplate"})
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    /**
     * 根据域名限流
     */
    @Bean(name = "hostAddrKeyResolver")
    public KeyResolver hostAddrKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
    }
}
