package yukx.security.auth.service.impl;

import yukx.security.auth.dao.entity.AUser;
import yukx.security.auth.dao.mappers.AUserMapper;
import yukx.security.auth.service.IAUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author yukx
 * @since 2020-09-21
 */
@Service
public class AUserServiceImpl extends ServiceImpl<AUserMapper, AUser> implements IAUserService {

}
