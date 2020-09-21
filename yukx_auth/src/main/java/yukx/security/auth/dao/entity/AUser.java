package yukx.security.auth.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author yukx
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("a_user")
@ApiModel(value="AUser对象", description="用户信息表")
public class AUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "年龄")
    @TableField("age")
    private Integer age;

    @ApiModelProperty(value = "性别 1:男  0:女")
    @TableField("sex")
    private Integer sex;

    @TableField("createTime")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "登陆账号")
    @TableField("login_name")
    private String loginName;

    @ApiModelProperty(value = "登陆密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "手机号")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;
}
