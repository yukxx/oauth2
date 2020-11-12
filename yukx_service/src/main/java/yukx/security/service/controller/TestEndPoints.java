package yukx.security.service.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import yukx.security.client.feign.UserClient;
import yukx.security.service.utils.AuthUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName TestEndPoints
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-25 17:16
 **/
@RestController
public class TestEndPoints {

    @Autowired
    private UserClient userClient;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id, HttpServletRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("用户名  : " + JSON.toJSONString(authentication.getPrincipal()));
        System.out.println("封装的传递信息  : " + AuthUtils.getReqUser(req));
        return "(Need Auth Request)product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        return "(No Auth Request)order id : " + id;
    }

    @ApiOperation("查询用户信息")
    @PostMapping("/getUserInfo.do")
    public String getUserInfo(String name, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String[] arr =token.split("bearer");
        token = arr[1];
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        OAuth2Authentication result = tokenStore.readAuthentication(accessToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userClient.getUserInfo(name);
    }
}
