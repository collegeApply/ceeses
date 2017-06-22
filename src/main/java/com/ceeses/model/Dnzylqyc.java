package com.ceeses.model;

/**
 * Created by zhaoshan on 2017/6/22.
 * 当年专业录取预测，需计算出来，初始化进来
 */
public class Dnzylqyc {
    private Integer year;
    private String collegeName;
    private String majorName;
    private String batchCode;
    private String category;
    private Integer enrollCount;
    private Float highGrade;
    private Float lowGrade;
    private Float avgGrade;
    private Integer highRanking;
    private Integer lowRanking;
    private Float avgRanking;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getEnrollCount() {
        return enrollCount;
    }

    public void setEnrollCount(Integer enrollCount) {
        this.enrollCount = enrollCount;
    }

    public Float getHighGrade() {
        return highGrade;
    }

    public void setHighGrade(Float highGrade) {
        this.highGrade = highGrade;
    }

    public Float getLowGrade() {
        return lowGrade;
    }

    public void setLowGrade(Float lowGrade) {
        this.lowGrade = lowGrade;
    }

    public Float getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Float avgGrade) {
        this.avgGrade = avgGrade;
    }

    public Integer getHighRanking() {
        return highRanking;
    }

    public void setHighRanking(Integer highRanking) {
        this.highRanking = highRanking;
    }

    public Integer getLowRanking() {
        return lowRanking;
    }

    public void setLowRanking(Integer lowRanking) {
        this.lowRanking = lowRanking;
    }

    public Float getAvgRanking() {
        return avgRanking;
    }

    public void setAvgRanking(Float avgRanking) {
        this.avgRanking = avgRanking;
    }
}
