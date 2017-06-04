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
    private Integer ranking;
    private Integer category;

    //以下可选
    private Integer batch;
    private Integer areaName;
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

    public Integer getAreaName() {
        return areaName;
    }

    public void setAreaName(Integer areaName) {
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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
