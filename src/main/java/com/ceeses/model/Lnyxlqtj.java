package com.ceeses.model;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/4.
 * 历年院校录取统计，主键年份，批次，科类 三个，关联于历年专业录取统计
 */
public class Lnyxlqtj {
    private Long id;
    private Integer year;
    private String collegeCode;
    private String collegeName;
    // 1.提前本科   2.第一批本科   3.第二批本科   4.第三批本科   5.省内预科
    // 6.提前专科   7.普通专科   8.省内本科定向   9.省内专科定向   A.提前本科定向
    // B.一本定向   C.二本定向   D.三本定向   E.提前专科定向   F.普通专科定向
    // H.贫困地区定向   Z.自主招生
    private String batchCode;
    //0-A
    /**
     0-文理综合   1-文史  2-外语(文)  3-艺术(文)  4-体育(文)  5-理工
     6-外语(理)  7-艺术(理) 8-体育(理)  9-单独考试  A-艺术(不分文理)
     */
    private String category;
    private Integer enrollCount;
    private Float highGrade;
    private Float lowGrade;
    private Float avgGrade;
    private Integer highRanking;
    private Integer lowRanking;
    private Float avgRanking;
    private List<Lnzylqtj> majorEnrollHistoryList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
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

    public List<Lnzylqtj> getMajorEnrollHistoryList() {
        return majorEnrollHistoryList;
    }

    public void setMajorEnrollHistoryList(List<Lnzylqtj> majorEnrollHistoryList) {
        this.majorEnrollHistoryList = majorEnrollHistoryList;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
