package com.ceeses.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/10.
 */
public class ProbabilityCalaDTO {

    private String collegeCode;
    private String areaName;
    private String collegeName;
    private String batchCode;
    private String batchName;
    private String collegeType;
    private String category;
    private String enrollCount;
    private Integer collegeRanking;

    //主键使年份，展示的使每年的录取情况
    private Map<Integer, Lnyxmc> yxRankingMap;

    //最终计算出某院校的录取概率
    //专业应该是跟院校平级的
    private double gaiLv;

    //主键专业名称包含每个年份某个专业的录取概率
    private Map<String, MajorEnrollDTO> majorEnrollDTOMap;

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getCollegeType() {
        return collegeType;
    }

    public void setCollegeType(String collegeType) {
        this.collegeType = collegeType;
    }

    public String getEnrollCount() {
        return enrollCount;
    }

    public void setEnrollCount(String enrollCount) {
        this.enrollCount = enrollCount;
    }

    public Integer getCollegeRanking() {
        return collegeRanking;
    }

    public void setCollegeRanking(Integer collegeRanking) {
        this.collegeRanking = collegeRanking;
    }

    public double getGaiLv() {
        return gaiLv;
    }

    public void setGaiLv(double gaiLv) {
        this.gaiLv = gaiLv;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<Integer, Lnyxmc> getYxRankingMap() {
        return yxRankingMap;
    }

    public void setYxRankingMap(Map<Integer, Lnyxmc> yxRankingMap) {
        this.yxRankingMap = yxRankingMap;
    }

    public Map<String, MajorEnrollDTO> getMajorEnrollDTOMap() {
        return majorEnrollDTOMap;
    }

    public void setMajorEnrollDTOMap(Map<String, MajorEnrollDTO> majorEnrollDTOMap) {
        this.majorEnrollDTOMap = majorEnrollDTOMap;
    }
}
