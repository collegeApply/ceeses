package com.ceeses.model;

import java.util.Date;

/**
 * 查询记录
 *
 * @author deng.zhang
 * @since 1.0 created on 2017/6/24
 */
public class QueryRecord {
    /**
     * ID
     */
    private Integer id;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 准考证号
     */
    private String examRegCode;
    /**
     * 高考分数
     */
    private Integer grade;
    /**
     * 分数名次
     */
    private Integer ranking;
    /**
     * 科别
     */
    private String category;
    /**
     * 批次
     */
    private String batch;
    /**
     * 院校所在省市
     */
    private String areaName;
    /**
     * 目标院校
     */
    private String targetSchool;
    /**
     * 目标专业
     */
    private String targetMajor;
    /**
     * 预测算法
     */
    private String algorithmType;
    /**
     * 排序方式
     */
    private String sortedType;
    /**
     * 查询时间
     */
    private Date queryTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
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

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public String getSortedType() {
        return sortedType;
    }

    public void setSortedType(String sortedType) {
        this.sortedType = sortedType;
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }
}
