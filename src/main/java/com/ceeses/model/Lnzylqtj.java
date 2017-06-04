package com.ceeses.model;

/**
 * Created by zhaoshan on 2017/6/4.
 * 历年专业录取统计，主键使院校统计id，专业名称
 */
public class Lnzylqtj {
    private Long id;
    private Long collegeEnrollId;
    private String majorName;
    private Integer enrollCount;
    private Integer highGrade;
    private Integer lowGrade;
    private Float avgGrade;
    private Integer highRanking;
    private Integer lowRanking;
    private Float avgRanking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollegeEnrollId() {
        return collegeEnrollId;
    }

    public void setCollegeEnrollId(Long collegeEnrollId) {
        this.collegeEnrollId = collegeEnrollId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
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
}
