package yukx.security.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName GatewayController
 * @Description TODO
 * @Author yukx
 * @Date 2020-11-04 15:08
 **/
@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @RequestMapping("/hello.do")
    public String hello(){
        return "hello";
    }
}
