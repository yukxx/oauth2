package yukx.security.common.myAop.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import yukx.security.common.myAop.processor.CustomizedBeanFactoryPostProcessor;
import yukx.security.common.myAop.processor.CustomizedBeanPostProcessor;

/**
 * @ClassName CustomizedImportSelector
 * @Description 自定义AOP实现，提交给Spring容器
 * @Author yukx
 * @Date 2021-03-17 17:14
 **/
public class CustomizedImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{CustomizedBeanFactoryPostProcessor.class.getName(), CustomizedBeanPostProcessor.class.getName()};
    }
}
