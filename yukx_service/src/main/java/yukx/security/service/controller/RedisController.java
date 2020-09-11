package yukx.security.service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yukx.security.service.utils.RedisUtils;

/**
 * @ClassName RedisController
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-11 16:57
 **/
@Api(description = "redis测试接口")
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Value("${fileLogin}")
    private String fileLogo;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/getFileLogo.do")
    public String getFileLogo() {
        return fileLogo;
    }

    @ApiOperation("保存redis值")
    @PostMapping("/setRedis.do")
    public String setRedis(String redisKey, String redisValue) {
        redisUtils.set(redisKey, redisValue, 60);
        return "suc";
    }

    @ApiOperation("获取redis值")
    @PostMapping("/getRedis.do")
    public String getRedis(String redisKey) {
        return String.valueOf(redisUtils.get(redisKey));
    }

    @ApiOperation("删除redis值")
    @PostMapping("/delRedis.do")
    public String delRedis(String redisKey) {
        redisUtils.del(redisKey);
        return "suc";
    }
}
