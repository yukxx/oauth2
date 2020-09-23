package yukx.security.service.service.impl;

import yukx.security.service.dao.entity.AAccount;
import yukx.security.service.dao.mappers.AAccountMapper;
import yukx.security.service.service.IAAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 现金账户表 服务实现类
 * </p>
 *
 * @author yukx
 * @since 2020-09-14
 */
@Service
public class AAccountServiceImpl extends ServiceImpl<AAccountMapper, AAccount> implements IAAccountService {

}
