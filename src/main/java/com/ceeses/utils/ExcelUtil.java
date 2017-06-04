package com.ceeses.utils;

/**
 * Created by zhaoshan on 2017/5/30.
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ceeses.dao.LnfsdxqDao;
import com.ceeses.dao.LnyxlqqkDao;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExcelUtil {
    //默认单元格内容为数字时格式
    private static DecimalFormat df = new DecimalFormat("0");
    // 默认单元格格式化日期字符串
    private static SimpleDateFormat sdf = new SimpleDateFormat(  "yyyy-MM-dd HH:mm:ss");
    // 格式化数字
    private static DecimalFormat nf = new DecimalFormat("0.00");

    @Autowired
    LnfsdxqDao lnfsdxqDao;

    public List<Lnfsdxq> getLiNianFenShuDuan(File file, Integer sheedIdex){
        List<Lnfsdxq> result = new ArrayList<>();
        try{
            file = new File("/Users/zhaoshan/Downloads/lyfsd.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(file);
            int sheetNum = wb.getNumberOfSheets();
            for (int i =0 ;i < sheetNum ;i ++ ){
                XSSFSheet sheet = wb.getSheetAt(i);

                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()){
                    Row row = rowIterator.next();

                    if (row.getCell(0).getCellTypeEnum().equals(CellType.STRING)){
                        continue;
                    }

                    Lnfsdxq lnfsdxq = new Lnfsdxq();
                    lnfsdxq.setYear(Integer.parseInt(sheet.getSheetName().substring(2)));
                    lnfsdxq.setCategory(sheet.getSheetName().substring(0,2));
                    lnfsdxq.setTotalGrade((int)row.getCell(0).getNumericCellValue());
                    lnfsdxq.setStudentCount((int)row.getCell(1).getNumericCellValue());
                    lnfsdxq.setRanking((int)row.getCell(2).getNumericCellValue());
                    lnfsdxq.setRuralAreaCount((int)row.getCell(3).getNumericCellValue());
                    lnfsdxq.setPasturingAreaCount((int)row.getCell(5).getNumericCellValue());
                    lnfsdxq.setPreReguarCount((int)row.getCell(7).getNumericCellValue());
                    lnfsdxq.setRuralTenAdd((int)row.getCell(9).getNumericCellValue());
                    lnfsdxq.setRuralTwenAdd((int)row.getCell(10).getNumericCellValue());
                    lnfsdxq.setHanTenAdd((int)row.getCell(11).getNumericCellValue());
                    lnfsdxq.setHanTwenAdd((int)row.getCell(12).getNumericCellValue());
                    lnfsdxq.setPasturingTenAdd((int)row.getCell(13).getNumericCellValue());
                    lnfsdxq.setPasturingTwenAdd((int)row.getCell(14).getNumericCellValue());
                    result.add(lnfsdxq);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        lnfsdxqDao.initLnfsdxq(result);

        return result;
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);//判断是否为数字
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static void readExcel2003(File file){

//            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
//            ArrayList<Object> colList;
//            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
//            HSSFSheet sheet = wb.getSheetAt(0);
//            HSSFRow row;
//            HSSFCell cell;
//            Object value;
    }



    public static void writeExcel(ArrayList<ArrayList<Object>> result,String path){
        if(result == null){
            return;
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        for(int i = 0 ;i < result.size() ; i++){
            HSSFRow row = sheet.createRow(i);
            if(result.get(i) != null){
                for(int j = 0; j < result.get(i).size() ; j ++){
                    HSSFCell cell = row.createCell(j);
                    cell.setCellValue(result.get(i).get(j).toString());
                }
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            wb.write(os);
        } catch (IOException e){
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        File file = new File(path);//Excel文件生成后存储的位置。
        OutputStream fos  = null;
        try
        {
            fos = new FileOutputStream(file);
            fos.write(content);
            os.close();
            fos.close();
        }catch (Exception e){
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