package yukx.security.common.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import yukx.security.common.model.UserInfoDto;
import yukx.security.common.utils.string.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @ClassName UserUtil
 * @Description TODO
 * @Author yukx
 * @Date 2020-11-13 17:24
 **/
public class UserUtil {

    /**
     * @return : yukx.security.common.model.UserInfoDto
     * @description : 获取用户信息
     * @author : yukx
     * @serialDate : 2020-11-13
     */
    public static UserInfoDto getUserInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String access_token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmptys(access_token))
            throw new RuntimeException("请登陆");

        access_token = access_token.substring(access_token.indexOf("bearer") + 6);
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey("SigningKey".getBytes("UTF-8")).parseClaimsJws(access_token).getBody();
        } catch (Exception e) {
            return null;
        }
        return JSON.parseObject((String) claims.get("userinfo"), UserInfoDto.class);
    }
}
