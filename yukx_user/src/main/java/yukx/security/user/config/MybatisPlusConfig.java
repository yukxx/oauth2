package yukx.security.user.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfig
 * @Description mybatis-plus
 * @Author yukx
 * @Date 2020-09-14 14:43
 **/
@Configuration
@MapperScan("yukx.security.user.dao.mappers")
public class MybatisPlusConfig {

    /**
     * @return : com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
     * @description : 设置分页插件
     * @author : yukx
     * @serialDate : 2020-09-14
     */
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        PaginationInnerInterceptor page = new PaginationInnerInterceptor();
        page.setOverflow(true);         // 请求的页面大于最大页后操作， true调回到首页
        page.setMaxLimit(100L);          // 每页最大数量
        page.setDbType(DbType.MYSQL);
        return page;
    }


    /**
     * @return : com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     * @description : 把分页插件添加到拦截器
     * @author : yukx
     * @serialDate : 2020-09-14
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(PaginationInnerInterceptor pageInterceptor) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(pageInterceptor);
        return interceptor;
    }
}
