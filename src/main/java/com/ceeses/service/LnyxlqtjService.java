package com.ceeses.service;

import com.ceeses.dao.LnyxlqqkDao;
import com.ceeses.dao.LnyxlqtjDao;
import com.ceeses.model.Lnyxlqqk;
import com.ceeses.model.Lnyxlqtj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 历年院校录取统计服务
 *
 * @author deng.zhang
 * @since 1.0 created on 2017/6/21
 */
@Service
public class LnyxlqtjService {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LnyxlqtjService.class);
    /**
     * 历年院校录取统计DAO
     */
    @Autowired
    private LnyxlqtjDao lnyxlqtjDao;
    /**
     * 历年院校录取情况DAO
     */
    @Autowired
    private LnyxlqqkDao lnyxlqqkDao;

    /**
     * 处理平均分数和平均排名
     */
    public void resolveAverage() {
        int count = lnyxlqtjDao.count();
        int pageSize = 100;
        int batch = count / pageSize + (count % pageSize > 0 ? 1 : 0);
        Map<String, Object> params = new HashMap<>(2);
        params.put("limit", pageSize);
        for (int i = 0; i < batch; i++) {
            params.put("offset", pageSize * i);
            List<Lnyxlqtj> lnyxlqtjs = lnyxlqtjDao.queryByPage(params);
            if (lnyxlqtjs != null && lnyxlqtjs.size() > 0) {
                for (Lnyxlqtj lnyxlqtj : lnyxlqtjs) {
                    Lnyxlqqk param = new Lnyxlqqk();
                    param.setYear(lnyxlqtj.getYear());
                    param.setSchoolName(lnyxlqtj.getCollegeName());
                    param.setBatchCode(lnyxlqtj.getBatchCode());
                    String categoryName = "";
                    if ("文史".equals(lnyxlqtj.getCategory())) {
                        categoryName = "1";
                    } else if ("理工".equals(lnyxlqtj.getCategory())) {
                        categoryName = "5";
                    }
                    param.setCategoryName(categoryName);
                    List<Lnyxlqqk> lnyxlqqks = lnyxlqqkDao.query(param);
                    float avgGrade = 0.0f;
                    float avgRanking = 0.0f;
                    if (lnyxlqqks != null && lnyxlqqks.size() > 0) {
                        float allGradeSum = 0.0f;
                        float allRankingSum = 0.0f;
                        for (Lnyxlqqk lnyxlqqk : lnyxlqqks) {
                            allGradeSum += lnyxlqqk.getScore();
                            allRankingSum += lnyxlqqk.getRanking();
                        }
                        avgGrade = allGradeSum / lnyxlqqks.size();
                        avgRanking = allRankingSum / lnyxlqqks.size();
                    }
                    lnyxlqtj.setAvgGrade(avgGrade);
                    lnyxlqtj.setAvgRanking(avgRanking);
                    lnyxlqtjDao.update(lnyxlqtj);
                }
                LOGGER.info("已处理{}条历年院校录取统计数据", pageSize * i + lnyxlqtjs.size());
            }
        }
    }
}
