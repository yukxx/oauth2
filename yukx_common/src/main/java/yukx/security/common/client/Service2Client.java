package yukx.security.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ClassName Service2Client
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-23 17:21
 **/
@FeignClient(name = "service2")
public interface Service2Client {

    /**
     * 测试feign
     * @return
     */
    @PostMapping("/user/testFeign.do")
    String testFeign();
}
