package yukx.security.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description swagger配置
 * @Author yukx
 * @Date 2020-09-11 17:18
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("yukx.security.service.controller")) // 需要生成文档的路径
                .paths(PathSelectors.any())
                .build();
    }

    // 文档说明
    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("spring-cloud-oauth2")
                .description("yukx 测试项目")
                .version("1.0")
                .build();
    }
}
