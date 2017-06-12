package com.ceeses.web.controller;

import com.ceeses.dao.*;
import com.ceeses.dto.ProbabilityCalaResponse;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Lnskfsx;
import com.ceeses.service.ProbabilityCalcService;
import com.ceeses.utils.CommonConstans;
import com.ceeses.utils.ExcelUtil;
import com.ceeses.web.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

    @Autowired
    ProbabilityCalcService probabilityCalcService;

    @RequestMapping("/getTargetColleges")
    @ResponseBody
    public ProbabilityCalaResponse getTargetColleges(ProbabilityCalcRequest probabilityCalcRequest) throws IOException {
        probabilityCalcRequest.setYear(2016);
        ProbabilityCalaResponse response = null;
        try {
            response = probabilityCalcService.getTargetColleges(probabilityCalcRequest);
        } catch (Exception e) {
            LOGGER.error("失败", e);
        }

        return response;
    }

    @RequestMapping("/getTargetCollegeWithMajor")
    @ResponseBody
    public ProbabilityCalaResponse getTargetCollegeWithMajor(ProbabilityCalcRequest probabilityCalcRequest) {
        probabilityCalcRequest.setYear(2016);
        ProbabilityCalaResponse response = null;
        try {
            response = probabilityCalcService.getTargetCollegeWithMajor(probabilityCalcRequest);
        } catch (Exception e) {
            LOGGER.error("失败", e);
        }

        return response;
    }


    @RequestMapping("/initLnfsdxq")
    @ResponseBody
    public JsonResult initLnfsdxq(String fileName) throws IOException {

        String path = this.getClass().getResource("/").getFile();

        //初始化传入的文件
        if (null != fileName && !"".equals(fileName)) {
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
        for (Lnskfsx lnskfsx : lnskfsxes) {
            CommonConstans.lnskfsxMap.put(lnskfsx.getYear() + "_" + lnskfsx.getBatch() + "_" + lnskfsx.getCategory(),
                    lnskfsx);
        }

        return new JsonResult();
    }


}
