package com.ceeses.service;

import com.ceeses.dao.CollegeInfoDao;
import com.ceeses.extractor.CollegeInfoExtractor;
import com.ceeses.model.CollegeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/10
 */
@Service
public class CollegeInfoService {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CollegeInfoService.class);
    @Autowired
    private CollegeInfoExtractor collegeInfoExtractor;
    @Autowired
    private CollegeInfoDao collegeInfoDao;

    /**
     * 根据省市查询院校信息
     *
     * @param area 省市名称
     * @return 院校信息列表
     */
    public List<CollegeInfo> findByArea(String area) {
        return collegeInfoDao.findByArea(area);
    }

    public void extract() {
        try {
            collegeInfoExtractor.setCollegeTypeMap(parseCollegeType());
            collegeInfoExtractor.setCollegeRankingMap(parseCollegeRanking());
        } catch (IOException e) {
            LOGGER.error("解析院校类型或者排名失败", e);
        }
        collegeInfoExtractor.extract();
    }

    private Map<String, String> parseCollegeType() throws IOException {
        Map<String, String> collegeTypeMap = new HashMap<>();
        BufferedReader reader985 = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/985院校.txt")));
        String line985;
        while ((line985 = reader985.readLine()) != null) {
            collegeTypeMap.put(line985, "985");
        }
        reader985.close();

        BufferedReader reader211 = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/211院校.txt")));
        String line211;
        while ((line211 = reader211.readLine()) != null) {
            String type = "211";
            if (collegeTypeMap.get(line211) != null) {
                type = collegeTypeMap.get(line211) + ",211";
            }
            collegeTypeMap.put(line211, type);
        }
        reader211.close();

        return collegeTypeMap;
    }

    private Map<String, Integer> parseCollegeRanking() throws IOException {
        Map<String, Integer> collegeRankingMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/全国院校排名.txt")));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] collegeNameAndRanking = line.split(",");
            if (collegeNameAndRanking.length == 2) {
                collegeRankingMap.put(collegeNameAndRanking[1], Integer.valueOf(collegeNameAndRanking[0]));
            }
        }
        reader.close();

        return collegeRankingMap;
    }
}
