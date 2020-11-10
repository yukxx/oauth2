package yukx.security.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName UserApplication
 * @Description TODO
 * @Author yukx
 * @Date 2020-11-10 14:30
 **/

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "yukx.security.client")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
