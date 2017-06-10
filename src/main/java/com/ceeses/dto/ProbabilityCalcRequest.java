package com.ceeses.dto;

/**
 * Created by zhaoshan on 2017/6/3.
 * 初步确定的输入参数根据用户输入结果查询
 */
public class ProbabilityCalcRequest {

    //以下必填
    private Integer year;
    private String name;
    private String examRegCode;
    private Integer grade;
    //名次
    private Integer ranking;
    //科类
    private String category;

    //以下可选
    private Integer batch;
    private String areaName;
    private String targetSchool;
    private String targetMajor;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExamRegCode() {
        return examRegCode;
    }

    public void setExamRegCode(String examRegCode) {
        this.examRegCode = examRegCode;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTargetSchool() {
        return targetSchool;
    }

    public void setTargetSchool(String targetSchool) {
        this.targetSchool = targetSchool;
    }

    public String getTargetMajor() {
        return targetMajor;
    }

    public void setTargetMajor(String targetMajor) {
        this.targetMajor = targetMajor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProbabilityCalcRequest{" +
                "year=" + year +
                ", name='" + name + '\'' +
                ", examRegCode='" + examRegCode + '\'' +
                ", grade=" + grade +
                ", ranking=" + ranking +
                ", category='" + category + '\'' +
                ", batch=" + batch +
                ", areaName=" + areaName +
                ", targetSchool='" + targetSchool + '\'' +
                ", targetMajor='" + targetMajor + '\'' +
                '}';
    }
}
