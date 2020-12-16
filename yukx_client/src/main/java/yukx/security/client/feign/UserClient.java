package yukx.security.client.feign;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yukx.security.client.fallback.UserClientFallback;

/**
 * @ClassName UserClient
 * @Description 用户
 * @Author yukx
 * @Date 2020-11-10 14:55
 **/
@FeignClient(name = "user", fallback = UserClientFallback.class)
public interface UserClient {

    /**
     * 查询用户信息
     */
    @PostMapping("/user/getUserInfo.do")
    String getUserInfo(@RequestParam("name") String name);


    @ApiOperation("测试事务")
    @PostMapping("/user/testTransaction.do")
    String testTransaction(@RequestParam("open") Integer open);
}
