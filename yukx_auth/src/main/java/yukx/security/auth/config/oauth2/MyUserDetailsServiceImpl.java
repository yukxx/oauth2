package yukx.security.auth.config.oauth2;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yukx.security.auth.dao.entity.AUser;
import yukx.security.auth.dao.mappers.AUserMapper;
import yukx.security.common.model.UserInfo;

import java.util.Arrays;

/**
 * @ClassName MyUserDetailsServiceImpl
 * @Description 用户信息服务
 * @Author yukx
 * @Date 2020-09-21 15:05
 **/
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AUserMapper aUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<AUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AUser::getLoginName, username);
        AUser user = aUserMapper.selectOne(wrapper);
        if (user == null)
            throw new UsernameNotFoundException("登陆用户[" + username + "]不存在");
        UserInfo userInfo = new UserInfo(user.getLoginName(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRoleName())));
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

}
