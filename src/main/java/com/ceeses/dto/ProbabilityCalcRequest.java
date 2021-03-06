package com.ceeses.dto;

/**
 * Created by zhaoshan on 2017/6/3.
 * 初步确定的输入参数根据用户输入结果查询
 */
public class ProbabilityCalcRequest {
    //以下必填
    private String queryCode;
    private Integer year;
    private String studentName;
    private String examRegCode;
    private Integer grade;
    //名次
    private Integer ranking;
    //科类
    private String category;

    //以下可选
    private String batch;
    private String areaName;
    /**
     * 院校类别
     */
    private String schoolType;
    private String targetSchool;
    private String targetMajor;

    //院校查询出后单独查询专业时用
    private String collegeCode;

    //排序方式1：录取概率高优先，2：学校排名高优先
    private Integer sortedType = 1;

    //查询算法,可以同时选个两种算法
    //1:位次法，2:线差法，3:两种皆有
    private String algorithmType;

    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
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

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    public Integer getSortedType() {
        return sortedType;
    }

    public void setSortedType(Integer sortedType) {
        this.sortedType = sortedType;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    @Override
    public String toString() {
        return "ProbabilityCalcRequest{" +
                "queryCode='" + queryCode + '\'' +
                ", year=" + year +
                ", studentName='" + studentName + '\'' +
                ", examRegCode='" + examRegCode + '\'' +
                ", grade=" + grade +
                ", ranking=" + ranking +
                ", category='" + category + '\'' +
                ", batch='" + batch + '\'' +
                ", areaName='" + areaName + '\'' +
                ", schoolType='" + schoolType + '\'' +
                ", targetSchool='" + targetSchool + '\'' +
                ", targetMajor='" + targetMajor + '\'' +
                ", collegeCode='" + collegeCode + '\'' +
                ", sortedType=" + sortedType +
                ", algorithmType='" + algorithmType + '\'' +
                '}';
    }
}
