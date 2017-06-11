package com.ceeses.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-05-29 21:10
 */
@Controller
public class IndexController extends BaseController {
    @RequestMapping("/")
    public String index() {
        return "forward:/index.html";
    }
}
