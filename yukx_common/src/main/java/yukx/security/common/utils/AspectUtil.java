package yukx.security.common.utils;

import yukx.security.common.myAop.holder.ProxyBeanHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName AspectUtil
 * @Description aop切面注解工具类
 * @Author yukx
 * @Date 2021-03-17 17:20
 **/
public class AspectUtil {

    /**
     * 指定切面注解类
     */
    public static final String ASPECT = "yukx.security.common.myAop.annotation.Aspect";

    /*
     * 指定切点注解类
     */
    public static final String POINTCUT = "yukx.security.common.myAop.annotation.Pointcut";

    /*
     * 指定前置通知注解类
     */
    public static final String BEFORE = "yukx.security.common.myAop.annotation.Before";

    /*
     * 指定后置通知注解类
     */
    public static final String AFTER = "yukx.security.common.myAop.annotation.After";

    /*
     * 指定环绕通知注解类
     */
    public static final String AROUND = "yukx.security.common.myAop.annotation.Around";

    /*
     * 存放AOP代理的全部目标类  目标类 ->(切面类,代理方法,通知注解)  如:com.jnu.example.blog.service.IArticleService -> [(com.jnu.example.blog.AspectTest, testBefore, yukx.security.common.myAop.annotation.Before)]
     */
    public static volatile Map<String, List<ProxyBeanHolder>> classzzProxyBeanHolder = new ConcurrentHashMap<>();
}
