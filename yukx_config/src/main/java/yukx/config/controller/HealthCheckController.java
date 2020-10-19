package yukx.config.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName HealthCheckController
 * @Description TODO
 * @Author yukx
 * @Date 2020-10-19 16:26
 **/
@RequestMapping("/health")
public class HealthCheckController {

    @RequestMapping("/doCheck")
    public int check() {
        return 0;
    }
}
