package yukx.security.common.myAop.holder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName ProxyBeanHolder
 * @Description 自定义数据结构 用于存放代理信息
 * @Author yukx
 * @Date 2021-03-17 17:23
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProxyBeanHolder {
    /*
     * 切面类名称
     */
    private Class<?> clazz;

    /*
     * 代理方法 如：testBefore
     */
    private String methodName;

    /*
     * 通知注解类名称 如：yukx.security.common.myAop.annotation.Before
     */
    private String annotationName;
}
