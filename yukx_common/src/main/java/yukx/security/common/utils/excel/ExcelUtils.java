package yukx.security.common.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import yukx.security.common.utils.string.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ExcelUtils
 * @Author: yukx
 * @Description: Excel工具类
 * @Date: 11:24 2019/7/9
 */
public class ExcelUtils {
    /**
     * 封装导入信息(目前只判断了String，Integer类型)
     *
     * @param clazz
     * @param rowlist
     * @return
     * @throws Exception
     */
    public static <T> T packageData(Class<T> clazz, List<String> rowlist) {
        if (clazz == null || rowlist == null || rowlist.size() <= 0)
            return null;
        try {
            Field[] fileds = clazz.getDeclaredFields();
            Object obj = clazz.newInstance();
            ImportCol importCol;
            Method method;
            for (Field f : fileds) {
                String fname = f.getName();
                importCol = f.getAnnotation(ImportCol.class);
                if (f.isAnnotationPresent(ImportCol.class)) {
                    method = clazz.getMethod("set" + captureName(fname), f.getType());
                    method.invoke(obj,
                            f.getType().getSimpleName().equalsIgnoreCase("String") ? rowlist.get(importCol.order())
                                    : Integer.valueOf(rowlist.get(importCol.order())));
                }
            }
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 获取行标题数据
     * @Param: [clazz]
     * @return: java.util.List<cn.lfn.market.sys.common.utils.poi.ExcelHeader>
     * @Author: yukx
     * @Date: 2020-07-23
     */
    private static List<ExcelHeader> getExcelHeaders(Class<?> clazz) {
        assert clazz != null;
        Field[] fields = clazz.getDeclaredFields();
        List<ExcelHeader> headerList = new ArrayList<>();
        for (Field field : fields) {
            String fname = field.getName();
            ExcelCol excelCol = field.getAnnotation(ExcelCol.class);
            if (field.isAnnotationPresent(ExcelCol.class))
                headerList.add(new ExcelHeader(fname, excelCol.title(), excelCol.order()));
        }
        Collections.sort(headerList);
        return headerList;
    }

    /**
     * 导出Excel的入口
     *
     * @param filename 导出文件名称
     * @param list     导出数据集合
     * @param clazz    导出数据集合对象字节码（需要使用 {@link ExcelCol}）
     *                 {@code cn.lfn.gem.dist.webserver.platform.account.controller.PlatAccountController }
     */
    public static void exportExcel(String filename, List<?> list, Class<?> clazz,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmptys(filename))
            filename = "exportFile";
        String agent = request.getHeader("USER-AGENT");
        boolean isIe = null != agent && agent.contains("MSIE") || null != agent
                && agent.contains("Trident") || null != agent && agent.contains("Edge");
        if (isIe) {
            // ie
            String fileName = java.net.URLEncoder.encode(filename, "UTF8");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + fileName + ".xlsx");
        } else if (null != agent && agent.contains("Mozilla")) {
            // 火狐,chrome等
            String fileName = new String(filename.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + fileName + ".xlsx");
        }

        List<ExcelHeader> headerList = getExcelHeaders(clazz);
        if (headerList.size() <= 0)
            throw new RuntimeException("请使用 @ExcelCol 标示" + clazz.getSimpleName() + "对象");
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        exportExcelV07(out, list, headerList);
        out.flush();
        out.close();
    }

    /**
     * {@link #exportExcel}
     */
    public static void exportExcel(String filename, List<?> list,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        assert list != null && list.size() > 0;
        exportExcel(filename, list, list.get(0).getClass(), request, response);
    }

    /**
     * @Description: 导出Excel2007
     * @Param: [out, list, headerList]
     * @return: void
     * @Author: yukx
     * @Date: 2020-07-23
     */
    private static void exportExcelV07(OutputStream out, List<?> list, List<ExcelHeader> headerList) throws Exception {
        Workbook wbk = doExportExcel(list, headerList);
        wbk.write(out);
    }

    /**
     * @Description: 导出数据
     * @Param: [rows, headers, isXSSF]
     * @return: org.apache.poi.ss.usermodel.Workbook
     * @Author: yukx
     * @Date: 2020-07-23
     */
    private static Workbook doExportExcel(List<?> rows, List<ExcelHeader> headers) throws Exception {
        SXSSFWorkbook wbk = new SXSSFWorkbook(100);
        CellStyle style = createStyle(wbk, 12);
        Sheet sheet = wbk.createSheet();
        Row row = sheet.createRow(0);
        Cell cell;
        for (int i = 0; i < headers.size(); i++) {
            ExcelHeader he = headers.get(i);
            cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(he.getCname());
        }
        for (int j = 0; j < rows.size(); j++) {
            Object obj = rows.get(j);
            Row rowData = sheet.createRow(j + 1);
            for (int g = 0; g < headers.size(); g++) {
                ExcelHeader header = headers.get(g);
                Method method = obj.getClass().getMethod("get" + captureName(header.getFname()));
                method.setAccessible(true);
                Object result = method.invoke(obj);

                Field field = obj.getClass().getDeclaredField(header.getFname());
                String matchResult = cellMatch(result, field);
                if (matchResult != null)
                    result = matchResult;

                cell = rowData.createCell(g);
                cell.setCellStyle(style);
                cell.setCellValue(convert(result));
            }
        }
        return wbk;
    }

    /**
     * @return : java.lang.String
     * @description : 类型匹配
     * @author : yukx
     * @serialDate : 2020-09-04
     */
    private static String cellMatch(Object result, Field field) {
        ExcelCol excelCol = field.getAnnotation(ExcelCol.class);
        if (!field.isAnnotationPresent(ExcelCol.class))
            return null;

        if (result instanceof Date) {
            String dateMatch = excelCol.dateMatch();
            if (StringUtils.isEmptys(dateMatch))
                return null;
            SimpleDateFormat sf = new SimpleDateFormat(dateMatch);
            return sf.format((Date) result);
        } else if (result instanceof Number) {
            ExcelCol.Match[] matches = excelCol.typeMatch();
            if (matches.length > 0) {
                ExcelCol.Match match = Arrays.stream(matches).filter(e -> e.key() == ((Number) result).intValue()).findFirst().orElse(null);
                return match != null ? match.value() : null;
            }
            ExcelCol.Option[] options = excelCol.option();
            if (options.length > 0) {
                double a = ((Number) result).doubleValue();
                for (ExcelCol.Option option : options) {
                    double b = option.value();
                    a = getA(a, option.opt(), b);
                }
                // 保留两位（四舍五入）
                return String.format("%.2f", a);
            }
        }
        return null;
    }

    private static double getA(double a, OptionEnum opt, double b) {
        switch (opt) {
            case ADD:
                a = a + b;
                break;
            case LESS:
                a = a - b;
                break;
            case MULTIPLY:
                a = a * b;
                break;
            case DIVISION:
                a = a / b;
                break;
        }
        return a;
    }

    public static String captureName(String name) {

        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    private static String convert(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }

    // 设置样式方法
    private static CellStyle createStyle(SXSSFWorkbook workbook, int fontsize) {

        // 1.2设置单元格样式
        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));
        // 设置水平居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置垂直居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 1.3设置字体
        Font font = workbook.createFont();
        // 设置字体为ARIAL
        font.setFontName(HSSFFont.FONT_ARIAL);
        // 设置字体颜色
//        font.setColor(HSSFColor.BLUE.index);
        // 设置字体大小
        font.setFontHeightInPoints((short) fontsize);
        // 将字体加入样式
        style.setFont(font);
        return style;
    }

    public String testSelect(){
        return "testSelect";
    }
}
