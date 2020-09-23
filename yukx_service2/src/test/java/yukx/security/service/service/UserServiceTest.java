package yukx.security.service.service;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yukx.security.service.dao.entity.AAccount;
import yukx.security.service.dao.entity.User;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private IAAccountService iaAccountService;

    @Test
    public void testSelectAll() {
        List<User> userList = userService.queryAll();
        //userList.forEach(e-> System.out.println(e.toString()));
        userList.forEach(System.out::println);
    }

    @Test
    public void testAccount() {
        AbstractWrapper wrapper = new QueryWrapper(new AAccount(), "acc_type");
        wrapper.eq("acc_type", 2);
        List<AAccount> aAccounts = iaAccountService.list(wrapper);
        aAccounts.forEach(System.out::println);
    }

    @Test
    public void testPage() {
        log.info("hello------yukx");
        Page page = new Page();
        page.setCurrent(1);
        page.setSize(4);
        IPage<AAccount> aAccounts = iaAccountService.page(page);
        aAccounts.getRecords().forEach(System.out::println);
    }
}