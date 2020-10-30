package yukx.security.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import yukx.security.service.dao.entity.Route;
import yukx.security.service.dao.mappers.RouteMapper;
import yukx.security.service.service.RouteService;
import yukx.security.service.utils.RedisUtils;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;

/**
 * @ClassName RouteServiceImpl
 * @Description TODO
 * @Author yukx
 * @Date 2020-10-29 14:27
 **/
@Slf4j
@Service
public class RouteServiceImpl implements RouteService {
    public static final String GATEWAY_ROUTES = "yukx_geteway_routes::";

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RouteMapper routeMapper;

    @Override
    @PostConstruct
    public void initRoute() {
        log.info("加载路由信息");

        List<Route> routeList = routeMapper.selectList(null);
        if (routeList.size() <= 0) {
            log.info("加载路由信息为空");
            return;
        }

        routeList.forEach(e -> {
            String str = JSON.toJSONString(getRouteDefinition(e));
            redisUtils.set(GATEWAY_ROUTES + e.getRouteId(), str);
            //redisUtils.hset(GATEWAY_ROUTES,"key",str);
        });

    }

    private RouteDefinition getRouteDefinition(Route route) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(route.getRouteId());
        definition.setUri(URI.create(route.getUri()));
        definition.setOrder(route.getSort());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            definition.setFilters(objectMapper.readValue(route.getFilters(), new TypeReference<List<FilterDefinition>>() {
            }));
            definition.setPredicates(objectMapper.readValue(route.getPredicates(), new TypeReference<List<PredicateDefinition>>() {
            }));
        } catch (Exception e) {
            log.error("加载路由异常:{}", e);
        }
        return definition;
    }
}
