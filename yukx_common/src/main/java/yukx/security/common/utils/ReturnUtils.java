package yukx.security.common.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName ReturnUtils
 * @Description TODO
 * @Author yukx
 * @Date 2020-10-30 11:22
 **/
public class ReturnUtils<T> {

    @Getter
    @Setter
    private Integer start;

    @Getter
    @Setter
    private Integer length;

    @Getter
    @Setter
    private Integer total;

    @Getter
    @Setter
    private T data;

    @Getter
    @Setter
    private int returnCode;

    @Getter
    @Setter
    private String returnMessage = "ok";

    public ReturnUtils error(String message) {
        this.returnCode = -1;
        this.returnMessage = message;
        return this;
    }

    public ReturnUtils error() {
        return error("操作失败");
    }
}
