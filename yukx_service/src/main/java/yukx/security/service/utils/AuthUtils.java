package yukx.security.service.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AuthUtils
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-25 17:02
 **/
public class AuthUtils {

    public static String getReqUser(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer");
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey("SigningKey".getBytes("UTF-8")).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
        String localUser = (String) claims.get("userinfo");
        // 拿到当前用户
        return localUser;
    }
}
