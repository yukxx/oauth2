package yukx.security.common.utils.excel;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName ExcelHeader
 * @Description Excel列标题和实体类属性名对应关系
 * @Author yukx
 * @Date 2020-07-23 14:15
 **/
@Getter
@Setter
public class ExcelHeader implements Comparable<ExcelHeader> {

    /**
     * 实体类属性名称
     */
    private String fname;

    /**
     * Excel标题列名称
     */
    private String cname;

    /**
     * 排序的标识
     */
    private int order;

    public ExcelHeader() {
    }

    public ExcelHeader(String fname, String cname, int order) {
        this.fname = fname;
        this.cname = cname;
        this.order = order;
    }

    @Override
    public int compareTo(ExcelHeader obj) {
        if (this.order > obj.order) {
            return 1;
        } else if (this.order < obj.order) {
            return -1;
        } else {
            return 0;
        }
    }
}
