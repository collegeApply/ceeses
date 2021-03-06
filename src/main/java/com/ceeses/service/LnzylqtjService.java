package com.ceeses.service;

import com.ceeses.dao.LnyxlqqkDao;
import com.ceeses.dao.LnzylqtjDao;
import com.ceeses.extractor.LnzylqtjExtractor;
import com.ceeses.model.Lnyxlqqk;
import com.ceeses.model.Lnzylqtj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 历年专业录取统计服务
 *
 * @author deng.zhang
 * @since 1.0 created on 2017/6/21
 */
@Service
public class LnzylqtjService {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LnzylqtjService.class);
    /**
     * 历年专业录取统计数据提取器
     */
    @Autowired
    private LnzylqtjExtractor lnzylqtjExtractor;
    /**
     * 历年专业录取统计DAO
     */
    @Autowired
    private LnzylqtjDao lnzylqtjDao;
    /**
     * 历年院校录取情况DAO
     */
    @Autowired
    private LnyxlqqkDao lnyxlqqkDao;

    public void extract() {
        LOGGER.info("清空历年专业录取统计表数据");
        lnzylqtjDao.empty();
        LOGGER.info("提取历年专业录取统计数据");
        lnzylqtjExtractor.extract();
    }

    /**
     * 处理平均分数和平均排名
     */
    public void resolveAverage() {
        int count = lnzylqtjDao.count();
        int pageSize = 100;
        int batch = count / pageSize + (count % pageSize > 0 ? 1 : 0);
        Map<String, Object> params = new HashMap<>(2);
        params.put("limit", pageSize);
        for (int i = 0; i < batch; i++) {
            params.put("offset", pageSize * i);
            List<Lnzylqtj> lnzylqtjs = lnzylqtjDao.queryByPage(params);
            if (lnzylqtjs != null && lnzylqtjs.size() > 0) {
                for (Lnzylqtj lnzylqtj : lnzylqtjs) {
                    Lnyxlqqk param = new Lnyxlqqk();
                    param.setYear(lnzylqtj.getYear());
                    param.setSchoolName(lnzylqtj.getCollegeName());
                    param.setBatchCode(lnzylqtj.getBatchCode());
                    String categoryName = "";
                    if ("文史".equals(lnzylqtj.getCategory())) {
                        categoryName = "1";
                    } else if ("理工".equals(lnzylqtj.getCategory())) {
                        categoryName = "5";
                    }
                    param.setCategoryName(categoryName);
                    param.setMajor(lnzylqtj.getMajorName());
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
                    lnzylqtj.setAvgGrade(avgGrade);
                    lnzylqtj.setAvgRanking(avgRanking);
                    lnzylqtjDao.update(lnzylqtj);
                }
                LOGGER.info("已处理{}条历年专业录取统计数据", pageSize * i + lnzylqtjs.size());
            }
        }
    }
}
