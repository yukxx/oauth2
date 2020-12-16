package yukx.security.common.autoconfig;

import org.springframework.context.annotation.Import;
import yukx.security.common.autoconfig.selector.ExcelSelector;

import java.lang.annotation.*;

/**
 * 往容器中注入 {@link yukx.security.common.utils.excel.ExcelUtils} 工具对象
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ExcelSelector.class)
public @interface EnableExcelUtil {
}
