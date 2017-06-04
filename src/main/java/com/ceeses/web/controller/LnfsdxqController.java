package com.ceeses.web.controller;

import com.ceeses.dao.LnfsdxqDao;
import com.ceeses.dao.LnskfsxDao;
import com.ceeses.extractor.LnyxlqqkExtractor;
import com.ceeses.model.Lnfsdxq;
import com.ceeses.model.Lnskfsx;
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

    @RequestMapping("/test")
    @ResponseBody
    public JsonResult extract() throws IOException {

        //excelUtil.getLiNianFenShuDuan(null, 0);
        Lnfsdxq lnfsdxq = lnfsdxqDao.queryTotalByGrade(2016,350);

        Integer g  = lnfsdxqDao.queryCountByGrade(lnfsdxq);
        System.out.println(lnfsdxq);
        System.out.println(g);

        List<Lnskfsx>  lnskfsxes = lnskfsxDao.queryLnskfsx(null);

        System.out.println(lnskfsxes);

        return new JsonResult();
    }



}
