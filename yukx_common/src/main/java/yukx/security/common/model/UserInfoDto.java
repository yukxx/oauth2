package yukx.security.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import yukx.security.common.utils.excel.ExcelCol;

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
public class UserInfoDto implements Serializable {

    private static final long serialVersionUID = 7081035384758175929L;
    /**
     * 名称
     */
    @ExcelCol(order = 0,title="名称")
    private String name;

    /**
     * 年龄
     */
    @ExcelCol(order = 1,title="年龄")
    private Integer age;

    /**
     * 性别
     */
    @ExcelCol(order = 2,title="性别")
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
}
