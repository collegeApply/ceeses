package com.ceeses.dto;

import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/10.
 */
public class MajorEnrollDTO {

    //专业录取的概率
    private double gaiLv;

    //主键年份，展示的使每年的录取情况
    private Map<Integer, Lnzymc> lnzymcMap;

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
}
