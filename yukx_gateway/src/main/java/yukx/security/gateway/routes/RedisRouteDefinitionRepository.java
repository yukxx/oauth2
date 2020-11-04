package yukx.security.gateway.routes;

import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName RedisRouteDefinitionRepository
 * @Description redis路由
 * @Author yukx
 * @Date 2020-10-20 17:51
 **/
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {
    public static final String GATEWAY_ROUTES = "yukx_geteway_routes::";

    @Resource
    private StringRedisTemplate redisTemplate;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        Objects.requireNonNull(redisTemplate.keys(GATEWAY_ROUTES + "*"))
                .forEach(key -> {
                    String vul = redisTemplate.opsForValue().get(key);
                    routeDefinitions.add(
                            JSON.parseObject(vul, RouteDefinition.class));

                });
        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

    public static void main(String[] args) {
        String vul = "{\"filters\":[{\"args\":{\"parts\":\"1\"},\"name\":\"StripPrefix\"}],\"id\":\"yukx-auth\",\"order\":0,\"predicates\":[{\"args\":{\"pattern\":\"/yukx-auth/**\"},\"name\":\"Path\"}],\"uri\":\"lb://yukx-auth:9020\"}";
        RouteDefinition definition = JSON.parseObject(vul, RouteDefinition.class);
        System.out.println(definition);
    }
}
