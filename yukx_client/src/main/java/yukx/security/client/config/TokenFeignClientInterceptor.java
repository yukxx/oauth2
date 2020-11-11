package yukx.security.client.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName TokenFeignClientInterceptor
 * @Description feign请求头处理
 * @Author yukx
 * @Date 2020-11-10 18:28
 **/
public class TokenFeignClientInterceptor implements RequestInterceptor {
    private static final String tokenHeader = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String token = request.getHeader(tokenHeader);
        template.header(tokenHeader, token);
    }
}
