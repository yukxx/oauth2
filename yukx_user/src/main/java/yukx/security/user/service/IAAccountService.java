package yukx.security.user.service;

import yukx.security.user.dao.entity.AAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 现金账户表 服务类
 * </p>
 *
 * @author yukx
 * @since 2020-12-15
 */
public interface IAAccountService extends IService<AAccount> {
    String testTransaction(Integer open);

}
