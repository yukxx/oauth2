package yukx.security.common.myAop.callback;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import yukx.security.common.myAop.holder.ProxyBeanHolder;
import yukx.security.common.utils.AspectUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName CustomizedProxyInterceptor
 * @Description cglib 回调
 * @Author yukx
 * @Date 2021-03-17 17:43
 **/
public class CustomizedProxyInterceptor implements MethodInterceptor {
    private List<ProxyBeanHolder> proxyBeanHolders;
    private ApplicationContext applicationContext;

    public CustomizedProxyInterceptor(ApplicationContext applicationContext, List<ProxyBeanHolder> proxyBeanHolders) {
        this.proxyBeanHolders = proxyBeanHolders;
        this.applicationContext = applicationContext;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        Class clazz = proxyBeanHolders.get(0).getClazz();
        Object proxyBean = this.applicationContext.getBean(clazz);

        if (proxyBeanHolders.get(0).getAnnotationName().equalsIgnoreCase(AspectUtil.BEFORE)) {
            Method method1 = clazz.getMethod(proxyBeanHolders.get(0).getMethodName());
            method1.invoke(proxyBean);
        }
        Object result = null;
        try {
            //前置通知
            result = methodProxy.invokeSuper(o, args);
            //返回通知, 可以访问到方法的返回值
            System.out.println(String.format("after method:%s execute", method.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            //异常通知, 可以访问到方法出现的异常
        }
        return result;
    }
}
