package com.ceeses.service;

import com.ceeses.dao.LnyxlqqkDao;
import com.ceeses.dao.LnyxlqtjDao;
import com.ceeses.dto.*;
import com.ceeses.model.Dnzsjh;
import com.ceeses.utils.CommonConstans;
import com.ceeses.utils.ExcelUtil;
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
    ExcelUtil excelUtil;

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

    public void initDnzsjh(){
        String wkFilePath = Thread.currentThread().getContextClassLoader().getResource("dnzsjh-lg.xlsx").getPath();
        List<Dnzsjh> dnzsjhslg = excelUtil.initDnzsjh(wkFilePath, "5" , 2017);

        for (Dnzsjh dnzsjh : dnzsjhslg){
            String collKey = dnzsjh.getCollegeName() + "_" + dnzsjh.getCategory() + "_" + dnzsjh.getBatch();
            String majorKey = collKey + "_"  + dnzsjh.getMajorName();
            if (!CommonConstans.collegeEnrollPlan.containsKey(collKey)){
                CommonConstans.collegeEnrollPlan.put(collKey, dnzsjh.getCollegeCount());
            }
            CommonConstans.majorEnrollPlan.put(majorKey , dnzsjh.getMajorCount());
        }

        String lkFilePath = Thread.currentThread().getContextClassLoader().getResource("dnzsjh-ws.xlsx").getPath();
        List<Dnzsjh> dnzsjhsws = excelUtil.initDnzsjh(lkFilePath,"1",2017);

        for (Dnzsjh dnzsjh : dnzsjhsws){
            String collKey = dnzsjh.getCollegeName() + "_" + dnzsjh.getCategory() + "_" + dnzsjh.getBatch();
            String majorKey = collKey + "_" + dnzsjh.getMajorName();
            if (!CommonConstans.collegeEnrollPlan.containsKey(collKey)){
                CommonConstans.collegeEnrollPlan.put(collKey, dnzsjh.getCollegeCount());
            }
            CommonConstans.majorEnrollPlan.put(majorKey , dnzsjh.getMajorCount());
        }
        LOGGER.info("初始化当年招生计划完成");
    }
}
