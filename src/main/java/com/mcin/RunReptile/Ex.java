package com.mcin.RunReptile;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Mcin on 2017/5/26.
 */
public class Ex {
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * excel方法-Poi在excel中绘图.
     * </p>
     * <p>
     * 创建人 IT山人 创建时间 2010-6-21 - 下午08:26:17
     * </p>
     * @return
     * @throws IOException
     */
    @Test
    public String excel() throws IOException {
        String savePath = "**.xls";
        OutputStream os = new FileOutputStream(savePath);
        //声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet("java在excel中绘图");
        //生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        //设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //生成一个字体
        HSSFFont font=workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short)16);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //把字体应用到当前的样式
        style.setFont(font);
        //声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        /**
         * dx1：起始单元格的x偏移量，如例子中的255表示直线起始位置距A1单元格左侧的距离；
         * dy1：起始单元格的y偏移量，如例子中的125表示直线起始位置距A1单元格上侧的距离；
         * dx2：终止单元格的x偏移量，如例子中的1023表示直线起始位置距C3单元格左侧的距离；
         * dy2：终止单元格的y偏移量，如例子中的150表示直线起始位置距C3单元格上侧的距离；
         * colFrom：起始单元格列序号，从0开始计算；
         * rowFrom：起始单元格行序号，从0开始计算，如例子中col1=0,row1=0就表示起始单元格为A1；
         * colTo：终止单元格列序号，从0开始计算；
         * rowTo：终止单元格行序号，从0开始计算，如例子中col2=2,row2=2就表示起始单元格为C3；
         */
        // default
        int dx1 = 0, dy1 = 0, dx2 = 1023, dy2 = 255;
        int colFrom = 0, rowFrom = 0, colTo = 5, rowTo = 5;

        HSSFClientAnchor bigValueAnchorShape = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom, (short)(colTo), rowTo);
        HSSFSimpleShape bigValueShape = patriarch.createSimpleShape(bigValueAnchorShape);
        bigValueShape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_OVAL);// 画椭圆 HSSFSimpleShape.OBJECT_TYPE_LINE画线
        bigValueShape.setFillColor(180, 200, 160);
        HSSFClientAnchor bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom+1), rowFrom+1, (short)(colTo-1), rowTo-1);
        HSSFTextbox bigValueTextbox = patriarch.createTextbox(bigValueAnchorTextBox);
        bigValueTextbox.setString(new HSSFRichTextString("相当于文本框中的文字"));
        bigValueTextbox.setLineStyle(HSSFSimpleShape.LINESTYLE_NONE);
        bigValueTextbox.setFillColor(180, 205, 160);

        workbook.write(os);
        os.close();

        return "excel";
    }
}
