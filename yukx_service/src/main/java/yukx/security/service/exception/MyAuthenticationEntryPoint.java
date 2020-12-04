package yukx.security.service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.error.AbstractOAuth2SecurityExceptionHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import yukx.security.common.utils.ReturnUtils;
import yukx.security.service.config.oauth2.ResourceConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @ClassName MyAuthenticationEntryPoint
 * @Description 资源服务异常处理（只针对oauth2 资源服务 的异常处理）
 * @Author yukx
 * @Date 2020-12-02 14:50
 * <p>
 * 1. 由于spring security的认证原理是通过注册到容器tomcat的filter链上，使得认证异常不能通过DispatcherServlet，所以@ExceptionHandler处理不到
 * 2. oauth2的认证异常和资源服务异常需要分开处理{@link OAuth2Exception}
 * 3. 访问资源服务器时，会经过 {@link OAuth2AuthenticationProcessingFilter} 过滤器, 验证token时报错会由{@link AuthenticationEntryPoint}接口处理
 * 默认实现类为 {@link OAuth2AuthenticationEntryPoint}
 * 4. 自定义好我们的异常处理器后需要在资源配置里面添加进去{@link ResourceConfiguration#configure(org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer)}
 **/
public class MyAuthenticationEntryPoint extends AbstractOAuth2SecurityExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        doHandle(request, response, authException);
    }

    @Override
    protected ResponseEntity<?> enhanceResponse(ResponseEntity<?> result, Exception authException) {
        Object body = result.getBody();
        String message = "服务器异常";
        if (body instanceof ClientAuthenticationException) {
            if (body instanceof InvalidGrantException)
                message = "无效授权";
            if (body instanceof InvalidClientException)
                message = "无效客户端";
            if (body instanceof InvalidTokenException)
                message = "无效token";
            if (body instanceof InvalidRequestException)
                message = "无效请求";
            if (body instanceof UnauthorizedClientException)
                message = "未经授权的客户端";
        }
        if (authException instanceof InsufficientAuthenticationException) {
            message = "当前用户为游客或者权限不足，请检查token和token_type";
        }
        ReturnUtils returnUtils = new ReturnUtils().error(result.getStatusCodeValue(), message);
        return ResponseEntity.status(result.getStatusCode()).body(returnUtils);
    }
}
