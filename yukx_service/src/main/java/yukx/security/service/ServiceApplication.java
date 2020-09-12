package yukx.security.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName ServiceApplication
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-11 15:01
 **/

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("yukx.security.service.dao.mappers")
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
