package com.ceeses.web.controller;

import com.ceeses.service.QueryCodeService;
import com.ceeses.web.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-06-29 21:12
 */
@Controller
@RequestMapping("/query/code")
public class QueryCodeController extends BaseController {
    /**
     * 查询码服务
     */
    @Autowired
    private QueryCodeService queryCodeService;

    /**
     * 生成查询码
     *
     * @return 结果
     */
    @RequestMapping("/generate")
    @ResponseBody
    public JsonResult generateQueryCode() {
        JsonResult result = new JsonResult();
        try {
            queryCodeService.randomGenerateCode();
            result.setMsg("生成查询码成功");
        } catch (Exception e) {
            result.setCode(JsonResult.ResultCode.FAILED);
            result.setMsg("生成查询码失败");
            LOGGER.error("生成查询码失败", e);
        }

        return result;
    }
}
