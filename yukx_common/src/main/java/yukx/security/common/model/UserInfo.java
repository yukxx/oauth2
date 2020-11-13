package yukx.security.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @Author yukx
 * @Date 2020-11-13 15:44
 **/
@Getter
@Setter
@ToString
public class UserInfo extends User implements Serializable {


    private static final long serialVersionUID = 7362908003040302633L;
    /**
     * 名称
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 登陆名称
     */
    private String loginName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 角色名称
     */
    private String roleName;

    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
