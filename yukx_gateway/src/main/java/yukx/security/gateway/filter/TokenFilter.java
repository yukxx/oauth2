package yukx.security.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName TokenFilter
 * @Description TODO
 * @Author yukx
 * @Date 2020-10-29 18:04
 **/
@Component
public class TokenFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest().mutate()
                .header("from", "gateway").build();
        return chain.filter(exchange.mutate().request(req.mutate().build()).build());
    }
}
