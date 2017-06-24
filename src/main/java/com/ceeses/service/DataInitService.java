package com.ceeses.service;

import com.ceeses.dao.DnyxlqycDao;
import com.ceeses.dao.LnyxlqqkDao;
import com.ceeses.dao.LnyxlqtjDao;
import com.ceeses.dto.*;
import com.ceeses.model.CollegeInfo;
import com.ceeses.model.Dnyxlqyc;
import com.ceeses.model.Dnzylqyc;
import com.ceeses.utils.CommonConstans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by zhaoshan on 2017/6/24.
 */
@Component
public class DataInitService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitService.class);

    @Autowired
    LnyxlqqkDao lnyxlqqkDao;

    @Autowired
    LnyxlqtjDao lnyxlqtjDao;

    @Autowired
    DnyxlqycDao dnyxlqycDao;

    public void initVolunteerInfos(Integer startYear){
        CommonConstans.volunteerMap.clear();
        CommonConstans.volunteerMajorMap.clear();
        VolunteerRequest volunteerRequest = new VolunteerRequest();
        volunteerRequest.setStartYear(startYear);
        List<VolunteerInfo> volunteerInfos = lnyxlqqkDao.queryVolunteerInfo(volunteerRequest);
        for (VolunteerInfo volunteerInfo : volunteerInfos){
            String key = volunteerInfo.getCollegeName() + "_" + volunteerInfo.getYear() +
                    "_" + volunteerInfo.getBatchCode() + "_" + volunteerInfo.getCategoryName();
            String volInfo = volunteerInfo.getVolunteer() + ":" + volunteerInfo.getVolunteerCount();
            if (CommonConstans.volunteerMap.containsKey(key)){
                CommonConstans.volunteerMap.put(key,CommonConstans.volunteerMap.get(key) + "--" + volInfo);
            } else {
                CommonConstans.volunteerMap.put(key,volInfo);
            }
        }

        List<VolunteerInfo>  volunteerMajorInfos = lnyxlqqkDao.queryVolunteerInfoWithMajor(volunteerRequest);
        for (VolunteerInfo volunteerInfo : volunteerMajorInfos){
            String key = volunteerInfo.getCollegeName() + "_" + volunteerInfo.getYear() +
                    "_" + volunteerInfo.getBatchCode() + "_" + volunteerInfo.getCategoryName() +
                    "_" + volunteerInfo.getMajor();
            String volInfo = volunteerInfo.getVolunteer() + ":" + volunteerInfo.getVolunteerCount();
            if (CommonConstans.volunteerMajorMap.containsKey(key)){
                CommonConstans.volunteerMajorMap.put(key,CommonConstans.volunteerMajorMap.get(key) + "--" + volInfo);
            } else {
                CommonConstans.volunteerMajorMap.put(key,volInfo);
            }
        }

    }

    /**
     * 根据历年数据预测当年--17年--分数和名次
     * 可以传入年份
     * 此预测需用到最新的分数线，17年数据必须17年分数线出来后能计算，根据分数线差计算
     * 名次可根据历年名次计算，分数线是根据差值预测
     * @param currentYear 需预测的年份，2016用来模拟，2017真实计算
     */
    public void calculateGradeAndRanking(Integer currentYear){

        Integer calcYears = 3;

        //主键：年份+院校+批次+科类
        Map<String, Lnyxmc> lnyxmcMap = new HashMap<>();

        //先存储所有历年出现过的专业的统计，主键年份+院校+批次+科类+专业
        //后续根据年份计算
        Map<String,Lnzymc> allMajorMap = new HashMap<>();

        //存储 院校+批次+科类
        Set<String> collegeKeys = new HashSet<>();

        //存储 院校+批次+科类+专业
        Set<String> majorKeys = new HashSet<>();

        //需存储数据库的当年预测信息
        Map<String, Dnyxlqyc> dnyxlqycs = new HashMap<>();

        //需存数据库的当年预测信息
        Map<String,Dnzylqyc> dnzylqycs = new HashMap<>();

        LOGGER.info("开始初始化当年分数和名次的预测数据");
        for (Integer yearIndex = currentYear - 1; yearIndex >= currentYear - calcYears ;yearIndex -- ) {
            ProbabilityCalcRequest queryTarget = new ProbabilityCalcRequest();
            queryTarget.setYear(yearIndex);
            List<CollegeEnrollHistory> histories = lnyxlqtjDao.queryCollegeEnrollHistory(queryTarget);

            for (CollegeEnrollHistory history : histories){
                //只预测文史和理工两个科类
                if (!(history.getCategory().equals("文史") || history.getCategory().equals("理工"))){
                    continue;
                }

                String collegeKey = history.getCollege_name() + "_" + history.getBatch_code()
                        + "_" + history.getCategory();
                collegeKeys.add(collegeKey);

                if (!dnyxlqycs.containsKey(collegeKey)) {
                    Dnyxlqyc dnyxlqyc = new Dnyxlqyc();
                    dnyxlqyc.setYear(currentYear);
                    dnyxlqyc.setCategory(history.getCategory());
                    dnyxlqyc.setBatchCode(history.getBatch_code());
                    dnyxlqyc.setCollegeCode(history.getCollege_code());
                    dnyxlqyc.setCollegeName(history.getCollege_name());
                    dnyxlqycs.put(collegeKey, dnyxlqyc);
                }

                majorKeys.add(collegeKey + "_" + history.getMajor_name());
                if (!dnzylqycs.containsKey(collegeKey + "_" + history.getMajor_name())) {
                    Dnzylqyc dnzylqyc = new Dnzylqyc();
                    dnzylqyc.setYear(currentYear);
                    dnzylqyc.setMajorName(history.getMajor_name());
                    dnzylqyc.setCollegeName(history.getCollege_name());
                    dnzylqyc.setBatchCode(history.getBatch_code());
                    dnzylqyc.setCategory(history.getCategory());

                    dnzylqycs.put(collegeKey + "_" + history.getMajor_name(),dnzylqyc);
                }

                if (!lnyxmcMap.containsKey(yearIndex + "_" + collegeKey)) {
                    Lnyxmc lnyxmc = new Lnyxmc();
                    lnyxmc.setYear(yearIndex);
                    lnyxmc.setAvgGrade(history.getAvg_grade());
                    lnyxmc.setAvgRanking(history.getAvg_ranking());
                    lnyxmc.setEnrollCunt(history.getEnroll_count());
                    lnyxmc.setHighGrade(history.getHigh_grade());
                    lnyxmc.setHighRanking(history.getHigh_ranking());
                    lnyxmc.setLowGrade(history.getLow_grade());
                    lnyxmc.setLowRanking(history.getLow_ranking());
                    lnyxmcMap.put(yearIndex + "_" + collegeKey, lnyxmc);
                }

                Lnzymc lnzymc = new Lnzymc();
                lnzymc.setLowGrade(history.getZy_low_grade());
                lnzymc.setAvgGrade(history.getZy_avg_grade());
                lnzymc.setHighGrade(history.getZy_high_grade());
                lnzymc.setHighRanking(history.getZy_high_ranking());
                lnzymc.setLowRanking(history.getZy_low_ranking());
                lnzymc.setAvgRanking(history.getZy_avg_ranking());
                lnzymc.setMajorName(history.getMajor_name());
                lnzymc.setEnrollCunt(history.getZy_enroll_count());
                lnzymc.setYear(yearIndex);
                allMajorMap.put(yearIndex + "_" + collegeKey + "_" + history.getMajor_name(),lnzymc);
            }

            LOGGER.info("当前年份{}，数据查询完毕" , yearIndex);
        }
        LOGGER.info("基础数据查询完毕");
        for (String collKey : collegeKeys){
            int counter = 0;
            int totalHighGrade = 0,totalLowGrade = 0,totalAvgGrade = 0,
                    totalHighRanking = 0,totalLowRanking = 0,totalAvgRanking = 0;

            Dnyxlqyc dnyxlqyc = dnyxlqycs.get(collKey);
            for (Integer yearIndex = currentYear - 1; yearIndex >= currentYear - calcYears ;yearIndex -- ){
                if (lnyxmcMap.containsKey(yearIndex + "_" + collKey)){

                    totalHighGrade += lnyxmcMap.get(yearIndex + "_" + collKey).getHighGrade();
                    totalLowGrade += lnyxmcMap.get(yearIndex + "_" + collKey).getLowGrade();
                    totalAvgGrade += lnyxmcMap.get(yearIndex + "_" + collKey).getAvgGrade();
                    totalHighRanking += lnyxmcMap.get(yearIndex + "_" + collKey).getHighRanking();
                    totalLowRanking += lnyxmcMap.get(yearIndex + "_" + collKey).getLowRanking();
                    totalAvgRanking += lnyxmcMap.get(yearIndex + "_" + collKey).getAvgRanking();
                    counter ++;
                }
            }

            dnyxlqyc.setHighRanking(totalHighRanking/counter);
            dnyxlqyc.setHighGrade((float)totalHighGrade/counter);

            dnyxlqyc.setLowRanking(totalLowRanking/counter);
            dnyxlqyc.setLowGrade((float)totalLowGrade/counter);

            dnyxlqyc.setAvgRanking((float)totalAvgRanking/counter);
            dnyxlqyc.setAvgGrade((float)totalAvgGrade/counter);

        }
        LOGGER.info("院校分数预测完毕");

        for (String majorKey : majorKeys){
            int counter = 0;
            int totalHighGrade = 0,totalLowGrade = 0,totalAvgGrade = 0,
                    totalHighRanking = 0,totalLowRanking = 0,totalAvgRanking = 0;

            Dnzylqyc dnzylqyc = dnzylqycs.get(majorKey);
            for (Integer yearIndex = currentYear - 1; yearIndex >= currentYear - calcYears ;yearIndex -- ){
                if (allMajorMap.containsKey(yearIndex + "_" + majorKey)){

                    totalHighGrade += allMajorMap.get(yearIndex + "_" + majorKey).getHighGrade();
                    totalLowGrade += allMajorMap.get(yearIndex + "_" + majorKey).getLowGrade();
                    totalAvgGrade += allMajorMap.get(yearIndex + "_" + majorKey).getAvgGrade();
                    totalHighRanking += allMajorMap.get(yearIndex + "_" + majorKey).getHighRanking();
                    totalLowRanking += allMajorMap.get(yearIndex + "_" + majorKey).getLowRanking();
                    totalAvgRanking += allMajorMap.get(yearIndex + "_" + majorKey).getAvgRanking();
                    counter ++;
                }
            }

            dnzylqyc.setHighRanking(totalHighRanking/counter);
            dnzylqyc.setHighGrade((float)totalHighGrade/counter);

            dnzylqyc.setLowRanking(totalLowRanking/counter);
            dnzylqyc.setLowGrade((float)totalLowGrade/counter);

            dnzylqyc.setAvgRanking((float)totalAvgRanking/counter);
            dnzylqyc.setAvgGrade((float)totalAvgGrade/counter);
        }

        LOGGER.info("专业分数预测完毕");

        dnyxlqycDao.batchSaveDnyxlqyc(new ArrayList<>(dnyxlqycs.values()));
        dnyxlqycDao.batchSaveDnzylqyc(new ArrayList<>(dnzylqycs.values()));

        LOGGER.info("插入数据库完成，算法结束");
    }


    public void initCurrentGradeAndRanking(Integer currentYear){
        List<CollegeEnrollYc>  enrollYcs = dnyxlqycDao.queryCollegeYc(currentYear);
        for (CollegeEnrollYc enrollYc : enrollYcs ){
            String category = "";
            if ("文史".equals(enrollYc.getCategory())){
                category = "1";
            }
            if ("理工".equals(enrollYc.getCategory())){
                category = "5";
            }
            if ("".equals(category)){
                continue;
            }

            String yxKey = enrollYc.getCollege_name() + "_" +  enrollYc.getBatch_code() +
                    "_" + category;

            if (!CommonConstans.dnyxlqycMap.containsKey(yxKey)){

                Dnyxlqyc dnyxlqyc = new Dnyxlqyc();

                dnyxlqyc.setHighRanking(enrollYc.getHigh_ranking().intValue());
                dnyxlqyc.setHighGrade(enrollYc.getHigh_grade());

                dnyxlqyc.setLowRanking(enrollYc.getLow_ranking().intValue());
                dnyxlqyc.setLowGrade(enrollYc.getLow_grade());

                dnyxlqyc.setAvgRanking(enrollYc.getAvg_ranking());
                dnyxlqyc.setAvgGrade(enrollYc.getAvg_grade());
                CommonConstans.dnyxlqycMap.put(yxKey, dnyxlqyc);
            }

            String zyKey = yxKey + "_" + enrollYc.getMajor_name();


            Dnzylqyc dnzylqyc = new Dnzylqyc();
            dnzylqyc.setHighRanking(enrollYc.getZy_high_ranking().intValue());
            dnzylqyc.setHighGrade(enrollYc.getZy_high_grade());

            dnzylqyc.setLowRanking(enrollYc.getZy_low_ranking().intValue());
            dnzylqyc.setLowGrade(enrollYc.getZy_low_grade());

            dnzylqyc.setAvgRanking(enrollYc.getZy_avg_ranking());
            dnzylqyc.setAvgGrade(enrollYc.getZy_avg_grade());

            CommonConstans.dnzylqycMap.put(zyKey,dnzylqyc);
        }

        LOGGER.info("完成已预测数据的加载");
    }


}
