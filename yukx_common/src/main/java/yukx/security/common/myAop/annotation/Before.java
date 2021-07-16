package yukx.security.common.myAop.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ClassName Before
 * @Description 前置通知注解
 * @Author yukx
 * @Date 2021-03-17 17:10
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {

    String value() default "";
}
