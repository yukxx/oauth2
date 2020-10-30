package yukx.security.service.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import yukx.security.common.utils.ReturnUtils;
import yukx.security.common.utils.string.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @ClassName GlobalInterceptor
 * @Description TODO
 * @Author yukx
 * @Date 2020-10-30 10:20
 **/
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求头开始验证");
        String secretKey = request.getHeader("from");
        if (StringUtils.isEmptys(secretKey) || !secretKey.equals("gateway")) {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(new ReturnUtils().error("请从网管访问")));
            return false;
        }
        return true;
    }
}
