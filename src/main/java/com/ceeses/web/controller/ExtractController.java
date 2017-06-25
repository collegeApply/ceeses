package com.ceeses.web.controller;

import com.ceeses.http.model.HttpResponseInfo;
import com.ceeses.http.util.HttpClientUtil;
import com.ceeses.service.CollegeInfoService;
import com.ceeses.service.LnyxlqqkService;
import com.ceeses.service.LnyxlqtjService;
import com.ceeses.service.LnzylqtjService;
import com.ceeses.web.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/8
 */
@RestController
@RequestMapping("/extract")
public class ExtractController extends BaseController {
    /**
     * 历年院校录取情况服务
     */
    @Autowired
    private LnyxlqqkService lnyxlqqkService;
    /**
     * 历年院校录取统计服务
     */
    @Autowired
    private LnyxlqtjService lnyxlqtjService;
    /**
     * 历年专业录取统计服务
     */
    @Autowired
    private LnzylqtjService lnzylqtjService;
    /**
     * 院校信息服务
     */
    @Autowired
    private CollegeInfoService collegeInfoService;

    @RequestMapping("/lnyxlqqk")
    @ResponseBody
    public JsonResult extractLnyxlqqk() throws IOException {
        login();
        lnyxlqqkService.extract();
        return new JsonResult();
    }

    @RequestMapping("/lnyxlqtj")
    @ResponseBody
    public JsonResult extractLnyxlqtj() throws IOException {
        login();
        lnyxlqtjService.extract();
        return new JsonResult();
    }

    @RequestMapping("/lnzylqtj")
    @ResponseBody
    public JsonResult extractLnzylqtj() throws IOException {
        login();
        lnzylqtjService.extract();
        return new JsonResult();
    }

    @RequestMapping("/college")
    @ResponseBody
    public JsonResult extractCollegeInfo() throws IOException {
        login();
        collegeInfoService.extract();
        return new JsonResult();
    }

    private static boolean login() throws IOException {
        // 登录页面
        String loginPageUrl = "http://www.qhzk.com/doLogings.action";

        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("userloginname", "13709759194");
        loginParams.put("userpass2", "mxj20150810");

        HttpResponseInfo responseInfo = HttpClientUtil.post(loginPageUrl, loginParams);

        return responseInfo.getStatusCode() == 200;
    }
}
