package yukx.security.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yukx.security.service.dao.entity.User;
import yukx.security.service.dao.mappers.UserMapper;
import yukx.security.service.service.UserService;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-12 15:05
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryAll() {
//        List<User> userList = userMapper.selectList(null);
        List<User> userList = userMapper.selectByMy();
        return userList;
    }

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
    }
}
