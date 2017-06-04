package com.ceeses.web.controller;

import com.ceeses.extractor.LnyxlqqkExtractor;
import com.ceeses.http.model.HttpResponseInfo;
import com.ceeses.http.util.HttpClientUtil;
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
 * @since 1.0 created on 2017/6/2
 */
@RestController
@RequestMapping("/lnyxlqqk")
public class LnyxlqqkController extends BaseController {
    @Autowired
    private LnyxlqqkExtractor lnyxlqqkExtractor;

    @RequestMapping("/extract")
    @ResponseBody
    public JsonResult extract() throws IOException {
        login();

        lnyxlqqkExtractor.extract();

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
