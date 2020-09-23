package yukx.security.service.dao.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yukx.security.service.dao.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> selectByMy();
}