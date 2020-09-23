package yukx.security.service;

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
public class Service2Application {
    public static void main(String[] args) {
        SpringApplication.run(Service2Application.class, args);
    }
}