package com.ceeses.model;

/**
 * 历年院校录取情况
 *
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-05-29 21:33
 */
public class Lnyxlqqk {
    /**
     * ID
     */
    private Integer id;
    /**
     * 年份
     */
    private Integer year;
    /**
     * 志愿号
     */
    private Integer volunteerNo;
    /**
     * 批次代码
     */
    private String batchCode;
    /**
     * 学生性别
     */
    private String studentGender;
    /**
     * 科别名称
     */
    private String categoryName;
    /**
     * 地区名称
     */
    private String areaName;
    /**
     * 地区代码
     */
    private String areaCode;
    /**
     * 录取院校名称
     */
    private String schoolName;
    /**
     * 录取专业
     */
    private String major;
    /**
     * 分数
     */
    private Float score;
    /**
     * 当前分数排名
     */
    private Integer ranking;
    /**
     * 投档单位名称
     */
    private String castArchiveUnitName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getVolunteerNo() {
        return volunteerNo;
    }

    public void setVolunteerNo(Integer volunteerNo) {
        this.volunteerNo = volunteerNo;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getCastArchiveUnitName() {
        return castArchiveUnitName;
    }

    public void setCastArchiveUnitName(String castArchiveUnitName) {
        this.castArchiveUnitName = castArchiveUnitName;
    }
}
