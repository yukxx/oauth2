package yukx.security.auth.config;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yukx.security.auth.dao.entity.AUser;
import yukx.security.auth.dao.mappers.AUserMapper;

import java.util.Arrays;
import java.util.List;

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
        return new User(user.getLoginName(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRoleName())));
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
