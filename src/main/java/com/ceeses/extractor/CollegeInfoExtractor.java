package com.ceeses.extractor;

import com.ceeses.dao.CollegeInfoDao;
import com.ceeses.http.util.HttpClientUtil;
import com.ceeses.model.CollegeInfo;
import com.ceeses.model.PageInfo;
import com.ceeses.utils.PageInfoUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/7
 */
@Component
public class CollegeInfoExtractor {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CollegeInfoExtractor.class);
    /**
     * 院校信息
     */
    private String collegeInfoPageUrl = "http://www.qhzk.com/yxList.action";
    /**
     * 院校类型Map
     */
    private Map<String, String> collegeTypeMap;
    /**
     * 院校排名Map
     */
    private Map<String, Integer> collegeRankingMap;
    @Autowired
    private CollegeInfoDao collegeInfoDao;

    public Integer extract() {
        LOGGER.info("提取院校信息开始......");
        long startTime = System.currentTimeMillis();

        PageInfo pageInfo = PageInfoUtil.extractPageInfo(collegeInfoPageUrl, null);
        LOGGER.info("提取分页信息结束, {}", pageInfo);

        if (pageInfo.getTotal() > 0) {
            LOGGER.info("清空院校信息表数据");
            collegeInfoDao.empty();
        }

        Map<String, String> params = new HashMap<>();

        int count = 0;
        for (int i = 1; i <= pageInfo.getTotalPage(); i++) {
            params.put("pageNum", String.valueOf(i));
            List<CollegeInfo> collegeInfos = extractCollegeInfoByPage(params);
            collegeInfoDao.batchSaveCollegeInfo(collegeInfos);
            count += collegeInfos.size();
            LOGGER.info("提取院校信息, 当前页第{}页, 已提取{}个", i, count);
        }

        LOGGER.info("提取院校信息结束, 总记录数: {}, 耗时: {}ms", count, System.currentTimeMillis() - startTime);
        return count;
    }

    /**
     * 分页提取院校信息信息
     *
     * @param params 参数
     * @return 院校信息信息列表
     */
    private List<CollegeInfo> extractCollegeInfoByPage(Map<String, String> params) {
        List<CollegeInfo> collegeInfos = new ArrayList<>();
        String html = HttpClientUtil.post(collegeInfoPageUrl, params).getHtml();
        if (html != null && !"".equals(html.trim())) {
            Document document = Jsoup.parse(html);
            Element dataTable = document.getElementById("GridView1");
            Elements dataTableTrs = dataTable.select("tr");
            // 第一个TR是表头
            if (dataTableTrs.size() > 1) {
                for (Element dataTableTr : dataTableTrs) {
                    Elements tds = dataTableTr.select("td");
                    if (tds.size() == 5) {
                        CollegeInfo collegeInfo = new CollegeInfo();
                        Element collegeNameElement = tds.get(0).getElementsByTag("a").get(0);
                        collegeInfo.setName(collegeNameElement.text());
                        collegeInfo.setCode(collegeNameElement.attr("href").split("\\?")[1].split("&")[0].split("=")[1]);
                        collegeInfo.setArea(tds.get(1).text());
                        collegeInfo.setEmail(tds.get(3).text());
                        collegeInfo.setType(collegeTypeMap.get(collegeInfo.getName()));
                        collegeInfo.setRanking(collegeRankingMap.get(collegeInfo.getName()));

                        collegeInfos.add(collegeInfo);
                    }
                }
            }
        }

        return collegeInfos;
    }

    public void setCollegeTypeMap(Map<String, String> collegeTypeMap) {
        this.collegeTypeMap = collegeTypeMap;
    }

    public void setCollegeRankingMap(Map<String, Integer> collegeRankingMap) {
        this.collegeRankingMap = collegeRankingMap;
    }
}
