package yukx.security.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author yukx
 * @Date 2020-11-10 14:31
 **/
@RestController
@RequestMapping("/user")
@Api(value = "用户", tags = "用户管理")
public class UserController {

    @ApiOperation("获取用户信息")
    @PostMapping("/getUserInfo.do")
    public String getUserInfo() {
        return "this is test user";
    }
}
