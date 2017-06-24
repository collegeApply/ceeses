package com.ceeses.web.controller;

import com.ceeses.dao.CollegeInfoDao;
import com.ceeses.dao.LnfsdxqDao;
import com.ceeses.dao.LnskfsxDao;
import com.ceeses.dao.LnyxlqtjDao;
import com.ceeses.dao.LnzylqtjDao;
import com.ceeses.dto.ProbabilityCalaResponse;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Lnskfsx;
import com.ceeses.model.QueryRecord;
import com.ceeses.service.ProbabilityCalcService;
import com.ceeses.service.QueryRecordService;
import com.ceeses.utils.CommonConstans;
import com.ceeses.utils.ExcelUtil;
import com.ceeses.web.result.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    /**
     * 查询记录服务
     */
    @Autowired
    private QueryRecordService queryRecordService;

    @RequestMapping("/getTargetColleges")
    @ResponseBody
    public ProbabilityCalaResponse getTargetColleges(ProbabilityCalcRequest probabilityCalcRequest) throws IOException {
        probabilityCalcRequest.setYear(2016);
        QueryRecord record = covertToQueryRecord(probabilityCalcRequest);
        ProbabilityCalaResponse response = null;
        try {
            response = probabilityCalcService.getTargetColleges(probabilityCalcRequest);
            queryRecordService.save(record);
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

        //初始化传入的文件，将传入参数修改成绝对路径
        if (null != fileName && !"".equals(fileName)) {
            String filePath = fileName;
            excelUtil.initLnfsdxq(filePath);
        } else {//初始化默认理工和文科历史数据
            String wkFilePath = path + "/lnfsd_wk.xlsx";
            excelUtil.initLnfsdxq(wkFilePath);

            String lkFilePath = path + "/lnfsd_lk.xlsx";
            excelUtil.initLnfsdxq(lkFilePath);
        }

        return new JsonResult();
    }

    @RequestMapping("/calcGradeAndRanking")
    @ResponseBody
    public JsonResult calcGradeAndRanking(Integer year) throws IOException {
        if (StringUtils.isEmpty(year)) {
            year = 2017;
        }
        probabilityCalcService.calcGradeAndRanking(year);
        return new JsonResult();
    }


    @RequestMapping("/testCalcSkx")
    @ResponseBody
    public JsonResult testCalcSkx() throws IOException {
        System.out.println(CommonConstans.getBatchByGrade(2016,466,"1","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,435,"1","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,466,"2","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,434,"3","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,406,"4","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,406,"5","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,389,"5","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,288,"6","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,466,"7","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,466,"8","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,435,"8","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,303,"9","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,466,"A","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,435,"A","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,466,"B","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,433,"C","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,406,"D","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,323,"E","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,254,"F","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,445,"H","1"));
        System.out.println(CommonConstans.getBatchByGrade(2016,416,"H","1"));
        return new JsonResult();
    }


    @RequestMapping("/initVolunteerInfos")
    @ResponseBody
    public JsonResult initVolunteerInfos() throws IOException {
        probabilityCalcService.initVolunteerInfos(2014);
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

    private QueryRecord covertToQueryRecord(ProbabilityCalcRequest request) {
        QueryRecord record = new QueryRecord();
        BeanUtils.copyProperties(request, record);
        record.setSortedType(String.valueOf(request.getSortedType()));
        record.setBatch(CommonConstans.batchMap.get(request.getBatch()));
        return record;
    }
}
