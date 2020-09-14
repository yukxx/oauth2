package yukx.security.service.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yukx.security.service.dao.entity.AAccount;
import yukx.security.service.dao.entity.User;

import java.util.List;

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
        List<AAccount> aAccounts = iaAccountService.list();
        aAccounts.forEach(System.out::println);
    }
}