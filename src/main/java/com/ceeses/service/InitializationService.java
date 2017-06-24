package com.ceeses.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 初始化服务
 *
 * @author deng.zhang
 * @since 1.0 created on 2017/6/25
 */
@Service
public class InitializationService {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializationService.class);

    public void init() {
        LOGGER.info("在这里初始化数据");
    }
}
