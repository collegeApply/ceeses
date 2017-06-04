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
    private String batchCode;
    private String category;
    private Integer enrollCount;
    private Integer highGrade;
    private Integer lowGrade;
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

    public Integer getHighGrade() {
        return highGrade;
    }

    public void setHighGrade(Integer highGrade) {
        this.highGrade = highGrade;
    }

    public Integer getLowGrade() {
        return lowGrade;
    }

    public void setLowGrade(Integer lowGrade) {
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
}
