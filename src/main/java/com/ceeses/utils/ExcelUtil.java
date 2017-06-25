package com.ceeses.utils;

/**
 * Created by zhaoshan on 2017/5/30.
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ceeses.dao.LnfsdxqDao;
import com.ceeses.model.Dnzsjh;
import com.ceeses.model.Lnfsdxq;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    //默认单元格内容为数字时格式
    private static DecimalFormat df = new DecimalFormat("0");
    // 默认单元格格式化日期字符串
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 格式化数字
    private static DecimalFormat nf = new DecimalFormat("0.00");

    @Autowired
    LnfsdxqDao lnfsdxqDao;

    public List<Lnfsdxq> initLnfsdxq(String filePath) {
        List<Lnfsdxq> result = new ArrayList<>();
        try {
            File file = new File(filePath);
            XSSFWorkbook wb = new XSSFWorkbook(file);
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = wb.getSheetAt(i);

                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    if (row.getCell(0).getCellTypeEnum().equals(CellType.STRING)) {
                        continue;
                    }

                    Lnfsdxq lnfsdxq = new Lnfsdxq();
                    lnfsdxq.setYear(Integer.parseInt(sheet.getSheetName().substring(2)));
                    lnfsdxq.setCategory(sheet.getSheetName().substring(0, 2));
                    lnfsdxq.setTotalGrade((int) row.getCell(0).getNumericCellValue());
                    lnfsdxq.setStudentCount((int) row.getCell(1).getNumericCellValue());
                    lnfsdxq.setRanking((int) row.getCell(2).getNumericCellValue());
                    lnfsdxq.setRuralAreaCount((int) row.getCell(3).getNumericCellValue());
                    lnfsdxq.setPasturingAreaCount((int) row.getCell(5).getNumericCellValue());
                    lnfsdxq.setPreReguarCount((int) row.getCell(7).getNumericCellValue());
                    lnfsdxq.setRuralTenAdd((int) row.getCell(9).getNumericCellValue());
                    lnfsdxq.setRuralTwenAdd((int) row.getCell(10).getNumericCellValue());
                    lnfsdxq.setHanTenAdd((int) row.getCell(11).getNumericCellValue());
                    lnfsdxq.setHanTwenAdd((int) row.getCell(12).getNumericCellValue());
                    lnfsdxq.setPasturingTenAdd((int) row.getCell(13).getNumericCellValue());
                    lnfsdxq.setPasturingTwenAdd((int) row.getCell(14).getNumericCellValue());
                    result.add(lnfsdxq);
                }
            }

        } catch (Exception e) {
            LOGGER.error("解析Excel出错", e);
        }

        lnfsdxqDao.initLnfsdxq(result);

        return result;
    }


    /**
     * 初始化当年的招生计划
     * 为了简单处理，此信息不存数据库了，每次应用启动时，直接从excel加载到内存
     * 文件从classpath读取一直有问题，所以服务器上从固定目录/root/dnzsjh-lg.xlsx  /root/dnzsjh-ws.xlsx 读取
     * 数据库事情以后再说
     * @param filePath
     * @param category
     * @return
     */
    public List<Dnzsjh> initDnzsjh(String filePath, String category, Integer year) {
        List<Dnzsjh> result = new ArrayList<>();
        try {
            File file = new File(filePath);
            XSSFWorkbook wb = new XSSFWorkbook(file);
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = wb.getSheetAt(i);

                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    if (row.getCell(0).getCellTypeEnum().equals(CellType.STRING)) {
                        continue;
                    }

                    Dnzsjh dnzsjh = new Dnzsjh();
                    dnzsjh.setYear(year);
                    dnzsjh.setCategory(category);
                    dnzsjh.setBatch(sheet.getSheetName().trim());
                    dnzsjh.setIndex((int)row.getCell(0).getNumericCellValue());
                    dnzsjh.setCollegeCode(String.valueOf(row.getCell(1).getNumericCellValue()).trim());
                    dnzsjh.setCollegeName(row.getCell(2).getStringCellValue().trim());
                    dnzsjh.setCollegeCount((int)row.getCell(3).getNumericCellValue());
                    if (row.getCell(4).getCellTypeEnum().equals(CellType.STRING)) {
                        dnzsjh.setMajorCode(String.valueOf(row.getCell(4).getStringCellValue()).trim());
                    }
                    if (row.getCell(4).getCellTypeEnum().equals(CellType.NUMERIC)) {
                        dnzsjh.setMajorCode(String.valueOf(row.getCell(4).getNumericCellValue()).trim());
                    }
                    String major  = row.getCell(5).getStringCellValue().trim();
                    String major2 = major.replace("（","(").replace("）",")");
                    dnzsjh.setMajorName(major2);
                    dnzsjh.setMajorCount((int)row.getCell(6).getNumericCellValue());

                    result.add(dnzsjh);
                }
            }

        } catch (Exception e) {
            LOGGER.error("解析Excel出错", e);
        }
        return result;
    }



    public static void writeExcel(ArrayList<ArrayList<Object>> result, String path) {
        if (result == null) {
            return;
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < result.size(); i++) {
            HSSFRow row = sheet.createRow(i);
            if (result.get(i) != null) {
                for (int j = 0; j < result.get(i).size(); j++) {
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(result.get(i).get(j).toString());
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        File file = new File(path);//Excel文件生成后存储的位置。
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DecimalFormat getDf() {
        return df;
    }

    public static void setDf(DecimalFormat df) {
        ExcelUtil.df = df;
    }

    public static SimpleDateFormat getSdf() {
        return sdf;
    }

    public static void setSdf(SimpleDateFormat sdf) {
        ExcelUtil.sdf = sdf;
    }

    public static DecimalFormat getNf() {
        return nf;
    }

    public static void setNf(DecimalFormat nf) {
        ExcelUtil.nf = nf;
    }


}