package yukx.security.service.service;

import yukx.security.service.dao.entity.User;

import java.util.List;

public interface UserService {

    List<User> queryAll();
}
