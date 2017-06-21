package com.ceeses.web.controller;

import com.ceeses.service.LnyxlqtjService;
import com.ceeses.web.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理数据控制器
 *
 * @author deng.zhang
 * @since 1.0 created on 2017/6/21
 */
@Controller
@RequestMapping("/resolve")
public class ResolveDataController extends BaseController {
    /**
     * 历年院校录取统计服务
     */
    @Autowired
    private LnyxlqtjService lnyxlqtjService;

    /**
     * 处理历年院校录取统计
     *
     * @return 结果
     */
    @RequestMapping("/lnyxlqtj")
    @ResponseBody
    public JsonResult resolveLnyxlqtj() {
        JsonResult result = new JsonResult();
        try {
            long startTime = System.currentTimeMillis();
            lnyxlqtjService.resolveAverage();
            result.setMsg("处理历年院校录取统计成功");
            LOGGER.info("处理历年院校录取统计成功， 耗时: {}ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            result.setCode(JsonResult.ResultCode.FAILED);
            result.setMsg("处理历年院校录取统计失败");
            LOGGER.error("处理历年院校录取统计失败", e);
        }

        return result;
    }
}
