package com.mcin.RunReptile;

import com.mcin.dao.InfoMapper;
import com.mcin.dao.impl.InfoDaoImpl;
import com.mcin.model.Info;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mcin on 2017/5/17.
 * 导出excel
 */
public class ExportExcel {

    private static final Logger logger = Logger.getLogger(ExportExcel.class);
    /**
     * 保存路径和文件名
     */
    static String FILE_NAME_PATH = "E:/慧聪___服装信息企业联系信息"+new Date().getTime()+".xls";

    /**
     * 手工构建一个excel
     * @return
     * @throws Exception
     */
    private static List<Info> getStudent() {
        InfoMapper infoMapper = new InfoDaoImpl();
        List<Info> list = new ArrayList<Info>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        list = infoMapper.selectInfo();
        return list;
    }

    public static void main(String[] args) {

        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("企业联系信息");

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow( 0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();

        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("公司名称");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("公司信息");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("公司网站");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        List list = ExportExcel.getStudent();

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow((int) i + 1);
            Info info = (Info) list.get(i);
            // 第四步，创建单元格，并设置值
            row
                    .createCell((short) 0)
                    .setCellValue(i+1);
            row
                    .createCell((short) 1)
                    .setCellValue(info.getCompanyName().replaceAll("\\s*", ""));
            row
                    .createCell((short) 2)
                    .setCellValue(info.getUserInfo());
            row
                    .createCell((short) 3)
                    .setCellValue(info.getHost());
            if (null != info.getCreateTime() ){
                cell = row.createCell((short) 4);
                cell.setCellValue(
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(info.getCreateTime())
                );
            }
        }
        // 第六步，将文件存到指定位置
        try {
            long startTime = System.currentTimeMillis();

            logger.info("*****开始进行导出文件*****");
            FileOutputStream fout = new FileOutputStream(FILE_NAME_PATH);
            wb.write(fout);
            fout.close();
            long ennTime = System.currentTimeMillis();

            logger.info(FILE_NAME_PATH + "  文件导出成功,共耗时："+ (double)(ennTime-startTime)/1000);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件导出出现异常  "+e.getMessage());
        }
    }
}