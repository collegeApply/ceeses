package com.ceeses.dto;

/**
 * Created by zhaoshan on 2017/6/3.
 * 院校分数统计，关联于返回结果，最高分，最低分，平均分
 */
public class Lnyxfs {

    private Integer year;
    private Integer highGrade;
    private Integer LowGrade;
    private Integer enrollCunt;
    private Float avgGrade;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getHighGrade() {
        return highGrade;
    }

    public void setHighGrade(Integer highGrade) {
        this.highGrade = highGrade;
    }

    public Integer getLowGrade() {
        return LowGrade;
    }

    public void setLowGrade(Integer lowGrade) {
        LowGrade = lowGrade;
    }

    public Integer getEnrollCunt() {
        return enrollCunt;
    }

    public void setEnrollCunt(Integer enrollCunt) {
        this.enrollCunt = enrollCunt;
    }

    public Float getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Float avgGrade) {
        this.avgGrade = avgGrade;
    }
}
