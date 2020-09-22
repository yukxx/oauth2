package yukx.security.common.utils.string;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName StringUtils
 * @Description TODO
 * @Author yukx
 * @Date 2020-09-22 14:00
 **/
public class StringUtils {


    /**
     * @Description: 所有为空=true
     * @Param: [strs]
     * @return: boolean
     * @Author: yukx
     * @Date: 2020-08-11
     */
    public static boolean isEmptys(String... strs) {
        List<String> stringList = Arrays.asList(strs);
        return stringList.stream().allMatch(e -> e == null || "".equals(e));
    }

    /**
     * @Description: 所有不为空=true
     * @Param: [strs]
     * @return: boolean
     * @Author: yukx
     * @Date: 2020-08-11
     */
    public static boolean isNotEmptys(String... strs) {
        List<String> stringList = Arrays.asList(strs);
        return stringList.stream().allMatch(e -> e != null && !"".equals(e));
    }
}
