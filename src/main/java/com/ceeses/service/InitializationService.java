package com.ceeses.service;

import com.ceeses.dao.LnfsdxqDao;
import com.ceeses.dao.LnskfsxDao;
import com.ceeses.dao.LnyxlqqkDao;
import com.ceeses.dao.LnyxlqtjDao;
import com.ceeses.model.Lnskfsx;
import com.ceeses.utils.CommonConstans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    LnskfsxDao lnskfsxDao;

    @Autowired
    DataInitService dataInitService;

    public void init() {
        //以下是三个初始化动作，以备查询前未初始化
        if (CommonConstans.lnskfsxMap.isEmpty()){
            List<Lnskfsx> lnskfsxes = lnskfsxDao.queryLnskfsx(null);
            for (Lnskfsx lnskfsx: lnskfsxes) {
                CommonConstans.lnskfsxMap.put(lnskfsx.getYear()+"_" + lnskfsx.getBatch() + "_" +lnskfsx.getCategory(),
                        lnskfsx);
            }
            LOGGER.info("初始化历年省控分数线完成");
        }

        if (CommonConstans.volunteerMap.isEmpty()){
            dataInitService.initVolunteerInfos(CommonConstans.currentYear - 4);
            LOGGER.info("初始化志愿号数据完成");
        }

        LOGGER.info("初始化数据完成");
    }
}
