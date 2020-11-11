package yukx.security.client.fallback;

import yukx.security.client.feign.UserClient;

/**
 * @ClassName UserClientFallback
 * @Description TODO
 * @Author yukx
 * @Date 2020-11-11 09:30
 **/
public class UserClientFallback implements UserClient {
    @Override
    public String getUserInfo(String name) {
        return "error";
    }
}
