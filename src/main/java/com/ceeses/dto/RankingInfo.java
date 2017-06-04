package com.ceeses.dto;

/**
 * Created by zhaoshan on 2017/6/4.
 */
public class RankingInfo {

    //当前年份
    private Integer currentYear;
    //当前名次
    private Integer currentRanking;
    //当前成绩
    private Integer currentGrade;
    //目标年份
    private Integer targetYear;

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    public Integer getCurrentRanking() {
        return currentRanking;
    }

    public void setCurrentRanking(Integer currentRanking) {
        this.currentRanking = currentRanking;
    }

    public Integer getTargetYear() {
        return targetYear;
    }

    public void setTargetYear(Integer targetYear) {
        this.targetYear = targetYear;
    }
}
