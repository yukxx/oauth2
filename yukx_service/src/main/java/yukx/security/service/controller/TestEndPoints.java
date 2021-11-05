package yukx.security.service.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import yukx.security.client.feign.UserClient;
import yukx.security.common.model.UserInfoDto;
import yukx.security.common.myAop.annotation.Before;
import yukx.security.common.utils.UserUtil;
import yukx.security.common.utils.excel.ExcelUtils;
import yukx.security.service.service.IAAccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName TestEndPoints
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-25 17:16
 **/
@Slf4j
@RestController
public class TestEndPoints {

    @Autowired
    private UserClient userClient;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private ExcelUtils excelUtils;

    @Autowired
    private IAAccountService accountService;

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id, HttpServletRequest req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("用户名  : " + JSON.toJSONString(authentication.getPrincipal()));
        System.out.println("封装的传递信息  : " + UserUtil.getUserInfo());
        return "(Need Auth Request)product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        log.info("测试excelselect" + excelUtils.testSelect());
        return "(No Auth Request)order id : " + id;
    }

    @Before
    @ApiOperation("查询用户信息")
    @PostMapping("/getUserInfo.do")
    public String getUserInfo(String name, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String[] arr = token.split("bearer");
        token = arr[1];
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        OAuth2Authentication result = tokenStore.readAuthentication(accessToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("--------登陆信息：{}", UserUtil.getUserInfo().toString());
        return userClient.getUserInfo(name);
    }

    @ApiOperation("测试事务")
    @PostMapping("/testTransaction.do")
    public String testTransaction(Integer open) {
        return accountService.testTransaction(open);
    }


    @ApiOperation("测试事务2")
    @PostMapping("/testTransaction2.do")
    public String testTransaction2(Integer open) {
        return accountService.testTransaction2(open);
    }

    @ApiOperation("测试导出")
    @PostMapping("/testExport.do")
    public void testExport() {
        List<UserInfoDto> list = new ArrayList<>();
        UserInfoDto dto = new UserInfoDto();
        dto.setName("yukx");
        dto.setAge(26);
        dto.setSex(1);
        list.add(dto);
        try {
            ExcelUtils.exportExcel("user", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
