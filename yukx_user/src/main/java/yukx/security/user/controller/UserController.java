package yukx.security.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yukx.security.user.service.IAAccountService;

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

    @Autowired
    private IAAccountService accountService;

    @ApiOperation("获取用户信息")
    @PostMapping("/getUserInfo.do")
    public String getUserInfo(@RequestParam("name") String name) {
        return "this is test user:" + name;
    }


    @ApiOperation("测试事务")
    @PostMapping("/testTransaction.do")
    public String testTransaction(@RequestParam("open") Integer open) {
        return accountService.testTransaction(open);
    }
}
