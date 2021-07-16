package yukx.security.common.myAop.annotation;

import org.springframework.context.annotation.Import;
import yukx.security.common.myAop.selector.CustomizedImportSelector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ClassName EnableAspectAutoProxy
 * @Description 开关注解
 * @Author yukx
 * @Date 2021-03-17 17:13
 **/
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomizedImportSelector.class)
public @interface EnableAspectAutoProxy {
}
