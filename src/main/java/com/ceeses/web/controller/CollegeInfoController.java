package com.ceeses.web.controller;

import com.ceeses.model.CollegeInfo;
import com.ceeses.service.CollegeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/11
 */
@RestController
@RequestMapping("/college")
public class CollegeInfoController extends BaseController {
    @Autowired
    private CollegeInfoService collegeInfoService;

    /**
     * 根据省市查询院校信息
     *
     * @param area 省市名称
     * @return 院校信息列表
     */
    @RequestMapping("/findByArea")
    @ResponseBody
    public List<CollegeInfo> findByArea(@RequestParam String area) {
        return collegeInfoService.findByArea(area);
    }
}
