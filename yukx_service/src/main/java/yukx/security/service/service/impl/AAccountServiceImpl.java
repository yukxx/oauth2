package yukx.security.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import yukx.security.client.feign.UserClient;
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

    @Autowired
    private UserClient userClient;

    @Override
    @Transactional
    public String testTransaction(Integer open) {
        AAccount a = getBaseMapper().selectById(32);
        AAccount b = getBaseMapper().selectById(34);
        a.setWithdrawMoney(a.getWithdrawMoney()-100);
        if(open==1)
            System.out.println(1/0);
        b.setWithdrawMoney(b.getWithdrawMoney()+100);
        getBaseMapper().updateById(a);
        getBaseMapper().updateById(b);
        return "suc";
    }

    @Override
    @Transactional
    public String testTransaction2(Integer open) {
        AAccount a = getBaseMapper().selectById(32);
        a.setWithdrawMoney(a.getWithdrawMoney()-100);
        getBaseMapper().updateById(a);
        userClient.testTransaction(open);
        if(open==1) {
            System.out.println(1/0);
        }

        return "suc";
    }
}
