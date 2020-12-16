package yukx.security.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yukx.security.user.dao.entity.AAccount;
import yukx.security.user.dao.mappers.AAccountMapper;
import yukx.security.user.service.IAAccountService;

/**
 * <p>
 * 现金账户表 服务实现类
 * </p>
 *
 * @author yukx
 * @since 2020-12-15
 */
@Service
public class AAccountServiceImpl extends ServiceImpl<AAccountMapper, AAccount> implements IAAccountService {

    @Override
    @Transactional
    public String testTransaction(Integer open) {
        AAccount b = baseMapper.selectById(34);
        b.setWithdrawMoney(b.getWithdrawMoney() + 100);
        baseMapper.updateById(b);
        return "suc";
    }
}
