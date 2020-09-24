package yukx.security.service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yukx.security.service.dao.entity.User;
import yukx.security.service.service.UserService;

import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-12 15:19
 **/

@Api(description = "user测试接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("查询所有用户")
    @PostMapping("/queryAll.do")
    public List<User> queryAll(){
        return userService.queryAll();
    }

    @PostMapping("/testFeign.do")
    public String testFeign(){
        return "yukx";
    }
}
