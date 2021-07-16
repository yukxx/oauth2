package yukx.security.common.myAop.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ClassName Pointcut
 * @Description 切点
 * @Author yukx
 * @Date 2021-03-17 17:10
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface Pointcut {

    String value() default "";
}
