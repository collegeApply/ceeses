package com.ceeses.dto;

import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/10.
 */
public class MajorEnrollDTO {

    //专业录取的概率
    private double gaiLv;

    /**
     * 针对界面，每年的Lnzymc数据已经包含招生计划、线差数据、名次数据三部分信息，
     */
    //主键年份，展示的使每年的录取情况
    //针对每个专业，会补充17年的数据
    private Map<Integer, Lnzymc> lnzymcMap;

    /**
     * 主键是年份，当前专业的志愿号等信息
     */
    private Map<Integer,String> volunteerInfoMap;

    public Map<Integer, Lnzymc> getLnzymcMap() {
        return lnzymcMap;
    }

    public void setLnzymcMap(Map<Integer, Lnzymc> lnzymcMap) {
        this.lnzymcMap = lnzymcMap;
    }

    public double getGaiLv() {
        return gaiLv;
    }

    public void setGaiLv(double gaiLv) {
        this.gaiLv = gaiLv;
    }

    public Map<Integer, String> getVolunteerInfoMap() {
        return volunteerInfoMap;
    }

    public void setVolunteerInfoMap(Map<Integer, String> volunteerInfoMap) {
        this.volunteerInfoMap = volunteerInfoMap;
    }
}
