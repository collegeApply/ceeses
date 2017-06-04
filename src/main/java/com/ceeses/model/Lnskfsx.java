package com.ceeses.model;

/**
 * Created by zhaoshan on 2017/6/3.
 * 历年省控线分数线详情，根据省控线和分数段计算名词映射
 */
public class Lnskfsx {

    private Integer year;
    private String batch;
    private String category;
    private Integer grade;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
