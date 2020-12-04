package yukx.security.common.exceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yukx.security.common.utils.ReturnUtils;

/**
 * @ClassName MyExceptionHandler
 * @Description TODO
 * @Author yukx
 * @Date 2020-12-04 15:12
 **/
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ReturnUtils exception(Exception e) {
        return new ReturnUtils().error(e.getMessage());
    }
}
