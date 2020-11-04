package yukx.security.service.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import yukx.security.common.utils.ReturnUtils;
import yukx.security.common.utils.string.StringUtils;
import yukx.security.service.utils.RedisUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @ClassName GlobalInterceptor
 * @Description 验证头部信息，防止请求绕过网关
 * @Author yukx
 * @Date 2020-10-30 10:20
 **/
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {
    private static String key = "from";

    @Setter
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求头开始验证");
        String redisKey = (String) redisUtils.get(key);
        String secretKey = request.getHeader(key);
        if (StringUtils.isEmptys(secretKey) || !secretKey.equals(redisKey)) {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(new ReturnUtils().error("请从网关访问")));
            return false;
        }
        return true;
    }
}
