package yukx.security.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestApiDocsController
 * @Description 测试接口
 * @Author yukx
 * @Date 2020-11-26 10:46
 **/
@RestController
@RequestMapping("/test")
public class TestApiDocsController {


    @Value("${yukx.name}")
    private String name;


    @Value("${yukx.age}")
    private Integer age;

    /**
     * hello接口
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello Nacos Discovery name:" + name + ",age:" + age;
    }
}
