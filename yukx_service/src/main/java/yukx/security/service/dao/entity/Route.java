package yukx.security.service.dao.entity;

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
 * 
 * </p>
 *
 * @author yukx
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("route")
@ApiModel(value="Route对象", description="路由信息表")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路由ID")
    @TableField("route_id")
    private String routeId;

    @ApiModelProperty(value = "断言")
    @TableField("predicates")
    private String predicates;

    @ApiModelProperty(value = "uri链接")
    @TableField("uri")
    private String uri;

    @ApiModelProperty(value = "过滤器")
    @TableField("filters")
    private String filters;

    @ApiModelProperty(value = "顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_time")
    private LocalDateTime modifyTime;


}
