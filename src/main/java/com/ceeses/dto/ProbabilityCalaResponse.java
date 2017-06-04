package com.ceeses.dto;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/3.
 * 初步确定的输出结果
 * 可能需要调整
 */
public class ProbabilityCalaResponse {

    private String collegeCode;
    private String areaName;
    private String collegeName;
    private String batchName;
    private String batchCode;
    private String collegeType;

    private String enrollCount;

    private Integer year;

    private Integer collegeRanking;
    private List<Lnyxmc> historyScoreInfoList;

    private List<Lnyxfs> historyGradeInfoList;

    private double gailv;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCollegeRanking() {
        return collegeRanking;
    }

    public void setCollegeRanking(Integer collegeRanking) {
        this.collegeRanking = collegeRanking;
    }

    public List<Lnyxmc> getHistoryScoreInfoList() {
        return historyScoreInfoList;
    }

    public void setHistoryScoreInfoList(List<Lnyxmc> historyScoreInfoList) {
        this.historyScoreInfoList = historyScoreInfoList;
    }

    public List<Lnyxfs> getHistoryGradeInfoList() {
        return historyGradeInfoList;
    }

    public void setHistoryGradeInfoList(List<Lnyxfs> historyGradeInfoList) {
        this.historyGradeInfoList = historyGradeInfoList;
    }

    public double getGailv() {
        return gailv;
    }

    public void setGailv(double gailv) {
        this.gailv = gailv;
    }

    /**
     * Created by zhaoshan on 2017/6/4.
     */
    public static class BatchInfo {
    }
}
