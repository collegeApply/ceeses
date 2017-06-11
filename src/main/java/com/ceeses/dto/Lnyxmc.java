package com.ceeses.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/3.
 * 院校名次统计，返回结果，最高名次，最低名次，平均名次
 */
public class Lnyxmc {

    private Integer year;
    private Float highRanking;
    private Float LowRanking;
    private Integer enrollCunt;
    private Float avgRanking;

    private Float highGrade;
    private Float LowGrade;
    private Float avgGrade;

    private double gaiLv = 0.0d;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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



    public Float getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Float avgGrade) {
        this.avgGrade = avgGrade;
    }

    public Float getHighRanking() {
        return highRanking;
    }

    public void setHighRanking(Float highRanking) {
        this.highRanking = highRanking;
    }

    public Float getLowRanking() {
        return LowRanking;
    }

    public void setLowRanking(Float lowRanking) {
        LowRanking = lowRanking;
    }

    public Float getHighGrade() {
        return highGrade;
    }

    public void setHighGrade(Float highGrade) {
        this.highGrade = highGrade;
    }

    public Float getLowGrade() {
        return LowGrade;
    }

    public void setLowGrade(Float lowGrade) {
        LowGrade = lowGrade;
    }

    public double getGaiLv() {
        return gaiLv;
    }

    public void setGaiLv(double gaiLv) {
        this.gaiLv = gaiLv;
    }
}
