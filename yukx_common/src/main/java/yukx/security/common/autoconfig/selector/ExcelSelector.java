package yukx.security.common.autoconfig.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName ExcelSelector
 * @Description TODO
 * @Author yukx
 * @Date 2020-12-12 15:57
 **/
public class ExcelSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        System.out.println(importingClassMetadata.getAnnotationTypes());
        return new String[]{"yukx.security.common.utils.excel.ExcelUtils"};
    }
}
