package com.ceeses.service;

import com.ceeses.dao.LnyxlqqkDao;
import com.ceeses.extractor.LnyxlqqkExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 历年院校录取情况服务
 *
 * @author deng.zhang
 * @since 1.0 created on 2017/6/25
 */
@Service
public class LnyxlqqkService {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LnyxlqqkService.class);
    /**
     * 历年院校录取情况数据提取器
     */
    @Autowired
    private LnyxlqqkExtractor lnyxlqqkExtractor;
    /**
     * 历年院校录取情况DAO
     */
    @Autowired
    private LnyxlqqkDao lnyxlqqkDao;

    public void extract() throws IOException {
        LOGGER.info("清空历年院校录取情况表数据");
        lnyxlqqkDao.empty();
        LOGGER.info("提取历年院校录取情况数据");
        lnyxlqqkExtractor.extract();
    }
}
