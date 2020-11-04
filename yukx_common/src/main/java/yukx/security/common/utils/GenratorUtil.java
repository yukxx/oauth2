package yukx.security.common.utils;


import java.util.UUID;

/**
 * 生成随机数
 */
public class GenratorUtil {

    /**
     * 日志
     */

    private GenratorUtil() {
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
