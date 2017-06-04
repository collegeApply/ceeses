package com.ceeses.web.controller;

import com.ceeses.web.result.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-05-29 21:10
 */
@RestController
public class IndexController extends BaseController {
    @RequestMapping("/index")
    @ResponseBody
    public JsonResult index() throws IOException {
        LOGGER.info("访问系统首页");
        return new JsonResult("Welcome to CEESES!");
    }
}
