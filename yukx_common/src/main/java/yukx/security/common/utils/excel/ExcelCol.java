package yukx.security.common.utils.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName ExcelCol
 * @Description 标记导出Excel的字段
 * @Author yukx
 * @Date 2020-07-23 13:55
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelCol {

    /**
     * Excel文档中的列标题名称
     */
    String title();

    /**
     * Excel列标题的位置标示
     */
    int order();

    /**
     * 类型匹配
     * {@link Integer}/{@link Byte} to {@link String}
     * 不可和 {@link #option()} 一起使用 此方法优先级高
     */
    Match[] typeMatch() default {};

    /**
     * 日期转字符串
     * {@link java.util.Date} to {@link String}
     */
    String dateMatch() default "yyyy-MM-dd HH:mm:ss";

    /**
     * 字段可以进行简单加减乘除操作 例如：this*0.01
     * 操作可以叠加
     * {@link Number}
     */
    Option[] option() default {};


    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface Option {

        /**
         * 操作符 {@link OptionEnum}
         */
        OptionEnum opt();

        double value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface Match {

        /**
         * 需要匹配的值
         */
        int key();

        /**
         * 匹配返回的值
         */
        String value();
    }
}
