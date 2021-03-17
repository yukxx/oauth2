package yukx.security.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TokenFilter
 * @Description TODO
 * @Author yukx
 * @Date 2020-10-29 18:04
 **/
@Component
public class TokenFilter implements GlobalFilter {

    private static String key = "from";
    private static long time = 60 * 10;    //  5分钟

    @Resource
    private StringRedisTemplate redisTemplate;


    public static void main(String[] args) {
        Map<String, Long> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i + "", System.currentTimeMillis());
        }
    }
    /**
     * 添加头部信息，防止请求绕过网关
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String uuid = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(uuid)) {
            uuid = getUUID();
            redisTemplate.opsForValue().set(key, uuid, time, TimeUnit.SECONDS);
        }
        ServerHttpRequest req = exchange.getRequest().mutate()
                .header(key, uuid).build();
        return chain.filter(exchange.mutate().request(req.mutate().build()).build());
    }

    private String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
