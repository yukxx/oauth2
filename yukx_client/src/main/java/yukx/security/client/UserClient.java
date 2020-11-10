package yukx.security.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName UserClient
 * @Description 用户
 * @Author yukx
 * @Date 2020-11-10 14:55
 **/
@FeignClient(name = "user")
public interface UserClient {
}
