package yukx.security.common.utils.excel;

import java.lang.annotation.*;

/**
 * @ClassName ImportCol
 * @Author: yukx
 * @Description: TODO()
 * @Date: 11:28 2019/7/9
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ImportCol
{
    // 说明
    String remarks() default "";

    // Excel列标题的位置标识
    int order();
}
