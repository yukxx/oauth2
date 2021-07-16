package yukx.security.common.myAop.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import yukx.security.common.myAop.callback.CustomizedProxyInterceptor;
import yukx.security.common.utils.AspectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @ClassName CustomizedBeanPostProcessor
 * @Description 给初始化成功后的bean生成代理
 * @Author yukx
 * @Date 2021-03-17 17:30
 **/
public class CustomizedBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * @return : java.lang.Object
     * @description : 初始化bean之前调用
     * @author : yukx
     * @serialDate : 2021-03-18
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * @return : java.lang.Object
     * @description : 初始化bean完成后调用
     * @author : yukx
     * @serialDate : 2021-03-18
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //获取bean class
        Class clazz = bean.getClass();
        //获取bean类名
        String className = clazz.getName();
        //如果这个bean是已经被代理后的  获取被代理前的类名
        className = className.substring(0, className.indexOf("$$") > 0 ? className.indexOf("$$") : className.length());
        Object object = bean;
        //对目标对象进行代理  采用cglib代理 继承方式
        if (AspectUtil.classzzProxyBeanHolder.containsKey(className)) {
            // 创建加强器，用来创建动态代理类
            Enhancer enhancer = new Enhancer();
            // 为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
            enhancer.setSuperclass(clazz);
            // 设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
            enhancer.setCallback(new CustomizedProxyInterceptor(applicationContext, AspectUtil.classzzProxyBeanHolder.get(className)));
            // 创建动态代理类对象并返回
            object = enhancer.create();

            //获取Class对象所表示的类或接口的所有(包含private修饰的)字段,不包括继承的字段
            Field[] fields = clazz.getDeclaredFields();
            //遍历字段
            for (Field field : fields) {
                //排除静态方法
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                //设置私有字段可以访问
                field.setAccessible(true);
                //实现相同字段赋值，解决代理对象中的自动注入bean为空的问题
                try {
                    field.set(object, field.get(bean));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }
}
