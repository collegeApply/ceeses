package com.ceeses.dto;

/**
 * Created by zhaoshan on 2017/6/3.
 * 院校名次统计，返回结果，最高名次，最低名次，平均名次
 */
public class Lnyxmc {

    private Integer year;
    private Integer highRanking;
    private Integer LowRanking;
    private Integer enrollCunt;
    private Float avgRanking;


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getHighRanking() {
        return highRanking;
    }

    public void setHighRanking(Integer highRanking) {
        this.highRanking = highRanking;
    }

    public Integer getLowRanking() {
        return LowRanking;
    }

    public void setLowRanking(Integer lowRanking) {
        LowRanking = lowRanking;
    }

    public Integer getEnrollCunt() {
        return enrollCunt;
    }

    public void setEnrollCunt(Integer enrollCunt) {
        this.enrollCunt = enrollCunt;
    }

    public Float getAvgRanking() {
        return avgRanking;
    }

    public void setAvgRanking(Float avgRanking) {
        this.avgRanking = avgRanking;
    }
}
