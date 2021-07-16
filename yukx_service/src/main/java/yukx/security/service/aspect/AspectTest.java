package yukx.security.service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import yukx.security.common.myAop.annotation.Aspect;
import yukx.security.common.myAop.annotation.Before;
import yukx.security.common.myAop.annotation.Pointcut;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName AspectTest
 * @Description TODO
 * @Author yukx
 * @Date 2021-03-18 15:14
 **/
@Component
@Aspect
@Slf4j
public class AspectTest {

    @Pointcut("yukx.security.service.controller")
    public void servicePointCut() {

    }

    @Before("servicePointCut()")
    public void testBefore() {
        log.info("-----------before测试成功");
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i);
        }
    }
}
