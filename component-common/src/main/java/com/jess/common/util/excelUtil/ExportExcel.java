package com.jess.common.util.excelUtil;

import com.jess.common.util.ReflectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: zhongxuexi
 * @Date: 2018/9/9 09:20
 * @Description: 动态导出EXCEL文档
 */
public class ExportExcel<T> {

    public void exportExcel(Collection<T> dataset, OutputStream out) {
        export("测试POI导出EXCEL文档", null, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out) {
        export("测试POI导出EXCEL文档", headers, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
        export("测试POI导出EXCEL文档", headers, dataset, out, pattern);
    }

    /**
     * 这是一个通用方法，利用java反射机制将java集合中符合条件的数据以excel的形式输出到指定IO设备上
     *
     * @param title   表格标题名
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合，集合中放java对象，对象的数据类型包括byte[](图片数据)
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或网络中
     * @param pattern 如果有时间数据，设定输出格式。默认问"yyyy-MM-dd"
     */
    @SuppressWarnings("unchecked")
    private void export(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        //设置表格默认列宽15个字节
        //sheet.setDefaultColumnWidth(15);
        //生成样式
        HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
        HSSFCellStyle columnStyle = this.getStyle(workbook);                    //单元格样式对象
        //声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        //定义注释的大小和位置，详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        //设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释!"));
        //设置注释作者，当鼠标移动到单元格上时，可以在状态栏中看到该内容
        comment.setAuthor("jess.zhong");
        //产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(columnTopStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            int columnSize = 0;
            T t = (T) it.next();
            if (t instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) t;//转换成map
                columnSize = map.keySet().size();
                int m = 0;
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    HSSFCell cell = row.createCell(m);
                    cell.setCellStyle(columnStyle);
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    String textValue = null;
                    //判断值的类型后进行强制类型转换
                    setValues(workbook, sheet, patriarch, row, index, m, cell, value, textValue);
                    m++;
                }
            } else {
                //利用放射，通过javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                try {
                    String[] fields = ReflectionUtils.getAllFields(t);
                    columnSize = fields.length;
                    for (int i = 0; i < fields.length; i++) {
                        HSSFCell cell = row.createCell(i);
                        cell.setCellStyle(columnStyle);
                        Object value = ReflectionUtils.getFieldValue(t, fields[i]);
                        String textValue = null;
                        //判断值的类型后进行强制类型转换
                        setValues(workbook, sheet, patriarch, row, index, i, cell, value, textValue);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            // 必须在单元格设值以后进行
            // 设置为根据内容自动调整列宽
            for (int k = 0; k < columnSize; k++) {
                sheet.autoSizeColumn(k);
            }
            // 处理中文不能自动调整列宽的问题
            setSizeColumn(sheet, columnSize);
        }
        //输出工作簿到输出流
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 单元格赋值
     *
     * @param workbook
     * @param sheet
     * @param patriarch
     * @param row
     * @param index
     * @param i
     * @param cell
     * @param value
     * @param textValue
     */
    public void setValues(HSSFWorkbook workbook, HSSFSheet sheet, HSSFPatriarch patriarch, HSSFRow row, int index, int i, HSSFCell cell, Object value, String textValue) {
        if (value instanceof byte[]) {
            //有图片时，设置行高为60px;
            row.setHeightInPoints(60);
            //设置图片所在的列宽为80px,
            sheet.setColumnWidth(i, (int) (35.7 * 80));
            byte[] bsValue = (byte[]) value;
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
            anchor.setAnchorType(AnchorType.MOVE_DONT_RESIZE);
            patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
        } else {
            //其他数据类型当字符串处理
            if (null != value) {
                textValue = value.toString();
            }
        }
        //如果不是图片数据，则设置列值
        if (null != textValue) {
            HSSFRichTextString richTextString = new HSSFRichTextString(textValue);
            HSSFFont font = workbook.createFont();
            font.setColor(HSSFColor.BLUE.index);
            richTextString.applyFont(font);
            cell.setCellValue(richTextString);
        }
    }

    /*
     * 列头单元格样式
     */
    private HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    /*
    * 列数据信息单元格样式
    */
    private HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    // 自适应宽度(中文支持)
    private void setSizeColumn(HSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 2 * 256);
        }
    }

}
