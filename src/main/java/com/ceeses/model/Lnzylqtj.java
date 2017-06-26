package com.ceeses.model;

/**
 * Created by zhaoshan on 2017/6/4.
 * 历年专业录取统计，主键使院校统计id，专业名称
 */
public class Lnzylqtj {
    private Long id;
    private Integer year;
    private Long collegeEnrollId;
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

    public Long getCollegeEnrollId() {
        return collegeEnrollId;
    }

    public void setCollegeEnrollId(Long collegeEnrollId) {
        this.collegeEnrollId = collegeEnrollId;
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

    @Override
    public String toString() {
        return "Lnzylqtj{" +
                "id=" + id +
                ", year=" + year +
                ", collegeEnrollId=" + collegeEnrollId +
                ", collegeName='" + collegeName + '\'' +
                ", majorName='" + majorName + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", category='" + category + '\'' +
                ", enrollCount=" + enrollCount +
                ", highGrade=" + highGrade +
                ", lowGrade=" + lowGrade +
                ", avgGrade=" + avgGrade +
                ", highRanking=" + highRanking +
                ", lowRanking=" + lowRanking +
                ", avgRanking=" + avgRanking +
                '}';
    }
}
