package yukx.security.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import yukx.security.client.config.TokenFeignClientInterceptor;
import yukx.security.common.exceptionHandler.MyExceptionHandler;

/**
 * @ClassName ServiceApplication
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-11 15:01
 **/

@SpringBootApplication
@EnableDiscoveryClient
@Import({TokenFeignClientInterceptor.class, MyExceptionHandler.class})
@EnableFeignClients(basePackages = "yukx.security.client.feign")
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}