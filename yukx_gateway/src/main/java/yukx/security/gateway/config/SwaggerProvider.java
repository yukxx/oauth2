package yukx.security.gateway.config;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.actuate.GatewayControllerEndpoint;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static yukx.security.gateway.routes.RedisRouteDefinitionRepository.GATEWAY_ROUTES;

/**
 * @ClassName SwaggerProvider
 * @Description TODO
 * @Author yukx
 * @Date 2020-11-09 10:13
 **/
@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {

    //private RouteService routeService;
    public static final String API_URI = "/v2/api-docs";

    @Autowired
    RouteLocator routeLocator;

    /*@Autowired
    GatewayProperties gatewayProperties;*/

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Resource
    private StringRedisTemplate redisTemplate;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));


        Objects.requireNonNull(redisTemplate.keys(GATEWAY_ROUTES + "*")).forEach(key -> {
            String vul = redisTemplate.opsForValue().get(key);
            RouteDefinition routeDefinition = JSON.parseObject(vul, RouteDefinition.class);

            assert routeDefinition != null;
            routeDefinition.getPredicates().forEach(e -> resources.add(swaggerResource(routeDefinition.getId(), e.getArgs().get("pattern").replace("/**", API_URI))));
        });
        /**
         * 参考{@link GatewayControllerEndpoint#routes()}
         */
        /*Mono<List<Route>> routes = this.routeLocator.getRoutes().collectList();
        Mono<Map<String, RouteDefinition>> routeDefs = this.routeDefinitionLocator
                .getRouteDefinitions().collectMap(RouteDefinition::getId);
        Mono.zip(routeDefs, routes).map(tuple -> {
            Map<String, RouteDefinition> defs = tuple.getT1();
            List<Route> routeList = tuple.getT2();
            List<Map<String, Object>> allRoutes = new ArrayList<>();

        });*/
        /*gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                        .replace("/**", API_URI)))));*/
        //resources.add(swaggerResource("service", "/api/service/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
