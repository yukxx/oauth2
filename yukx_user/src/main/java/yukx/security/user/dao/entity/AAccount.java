package yukx.security.user.dao.entity;

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
 * 现金账户表
 * </p>
 *
 * @author yukx
 * @since 2020-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("a_account")
@ApiModel(value="AAccount对象", description="现金账户表")
public class AAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("id")
    private Integer id;

    @ApiModelProperty(value = "账号类型0:代表平台；1: 表示代理 2 表示供应商")
    @TableField("acc_type")
    private Integer accType;

    @ApiModelProperty(value = "平台的记录0；非0代表代理/供应商的id")
    @TableField("obj_id")
    private Long objId;

    @ApiModelProperty(value = "可提现金额（分）")
    @TableField("withdraw_money")
    private Integer withdrawMoney;

    @ApiModelProperty(value = "冻结金额（分）")
    @TableField("freeze_money")
    private Integer freezeMoney;

    @ApiModelProperty(value = "提现中金额（分）")
    @TableField("withdrawing_money")
    private Integer withdrawingMoney;

    @ApiModelProperty(value = "状态：0：正常；-1：已删除")
    @TableField("obj_status")
    private Integer objStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "代理名称")
    @TableField("agent_name")
    private String agentName;

    @ApiModelProperty(value = "代理电话")
    @TableField("agent_mobile")
    private String agentMobile;

    @ApiModelProperty(value = "已提现金额（只增不减）")
    @TableField("withdrawed_money")
    private Integer withdrawedMoney;

    @ApiModelProperty(value = "结算时间")
    @TableField("settl_time")
    private LocalDateTime settlTime;


}
