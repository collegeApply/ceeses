package com.ceeses.web.controller;

import com.ceeses.web.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-05-29 21:15
 */
public class BaseController {
    /**
     * logger
     */
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());
    /**
     * 请求
     */
    @Autowired
    protected HttpServletRequest request;
    /**
     * 响应
     */
    @Autowired
    protected HttpServletResponse response;

    /**
     * 统一处理接口调用产生的异常
     *
     * @return json格式的错误信息
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult handleException(Exception e, HttpServletRequest request) {
        LOGGER.error(request.getRequestURI(), e);
        return new JsonResult(JsonResult.ResultCode.FAILED, e.getMessage(), null);
    }
}
