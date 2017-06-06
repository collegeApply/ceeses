package com.ceeses.web.controller;

import com.ceeses.dao.*;
import com.ceeses.model.*;
import com.ceeses.utils.CommonConstans;
import com.ceeses.utils.ExcelUtil;
import com.ceeses.web.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaoshan on 2017/6/4.
 */
@RestController
@RequestMapping("/lnfsdxq")
public class LnfsdxqController extends BaseController {


    @Autowired
    private ExcelUtil excelUtil;

    @Autowired
    LnfsdxqDao lnfsdxqDao;

    @Autowired
    LnskfsxDao lnskfsxDao;

    @Autowired
    LnyxlqtjDao lnyxlqtjDao;

    @Autowired
    LnzylqtjDao lnzylqtjDao;

    @Autowired
    CollegeInfoDao collegeInfoDao;

    @RequestMapping("/test")
    @ResponseBody
    public JsonResult test() throws IOException {
//        Lnfsdxq lnfsdxq = lnfsdxqDao.queryTotalByGrade(2016,350);
//        Integer g  = lnfsdxqDao.queryCountByGrade(lnfsdxq);
//        System.out.println(lnfsdxq);

//        List<Lnyxlqtj> lnyxlqtjs = new ArrayList<>();
//        Lnyxlqtj lnyxlqtj = new Lnyxlqtj();
//        lnyxlqtj.setYear(2016);
//        lnyxlqtj.setAvgGrade(634.56f);
//        lnyxlqtj.setAvgRanking(23.5f);
//        lnyxlqtj.setBatchCode("2");
//        lnyxlqtj.setCategory("5");
//        lnyxlqtj.setCollegeCode("10001");
//        lnyxlqtj.setCollegeName("qinghuadaxue");
//        lnyxlqtj.setEnrollCount(23);
//        lnyxlqtj.setHighGrade(660);
//        lnyxlqtj.setHighRanking(18);
//        lnyxlqtj.setLowGrade(600);
//        lnyxlqtj.setLowRanking(34);
//
//        lnyxlqtjs.add(lnyxlqtj);
//        lnyxlqtjDao.batchSaveLnyxlqtj(lnyxlqtjs);


//        List<Lnzylqtj> lnzylqtjs = new ArrayList<>();
//        Lnzylqtj lnzylqtj = new Lnzylqtj();
//        lnzylqtj.setCollegeEnrollId(22l);
//        lnzylqtj.setMajorName("zidonghua");
//        lnzylqtj.setAvgGrade(638.56f);
//        lnzylqtj.setAvgRanking(23.5f);
//        lnzylqtj.setEnrollCount(23);
//        lnzylqtj.setHighGrade(658);
//        lnzylqtj.setHighRanking(19);
//        lnzylqtj.setLowGrade(620);
//        lnzylqtj.setLowRanking(31);
//        lnzylqtjs.add(lnzylqtj);
//        lnzylqtjDao.batchSaveLnzylqtj(lnzylqtjs);


        List<CollegeInfo> collegeInfos = new ArrayList<>();
        CollegeInfo collegeInfo = new CollegeInfo();
        collegeInfo.setArea("01");
        collegeInfo.setCode("10001");
        collegeInfo.setCreateTime(new Date());
        collegeInfo.setEmail("bjtc@15677.com");
        collegeInfo.setName("北京大学");
        collegeInfo.setRanking(1);
        collegeInfo.setType("985");
        collegeInfo.setUpdateTime(new Date());
        collegeInfos.add(collegeInfo);
        collegeInfoDao.batchSaveCollegeInfo(collegeInfos);


        return new JsonResult();
    }


    @RequestMapping("/initLnfsdxq")
    @ResponseBody
    public JsonResult initLnfsdxq(String fileName) throws IOException {

        String path = this.getClass().getResource("/").getFile();

        //初始化传入的文件
        if (null != fileName &&  !"".equals(fileName)){
            String filePath = path + "/" + fileName;
            excelUtil.initLnfsdxq(filePath);
        } else {//初始化默认理工和文科历史数据
            String wkFilePath = path + "/lnfsd_wk.xlsx";
            excelUtil.initLnfsdxq(wkFilePath);

            String lkFilePath = path + "/lnfsd_lk.xlsx";
            excelUtil.initLnfsdxq(lkFilePath);
        }

        return new JsonResult();
    }

    @RequestMapping("/initLnskfs")
    @ResponseBody
    public JsonResult initLnskfs() throws IOException {

        List<Lnskfsx> lnskfsxes = lnskfsxDao.queryLnskfsx(null);
        for (Lnskfsx lnskfsx: lnskfsxes) {
            CommonConstans.lnskfsxMap.put(lnskfsx.getYear()+"_" + lnskfsx.getBatch() + "_" +lnskfsx.getCategory(),
                    lnskfsx);
        }

        return new JsonResult();
    }



}
