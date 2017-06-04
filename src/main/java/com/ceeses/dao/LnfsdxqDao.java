package com.ceeses.dao;

import com.ceeses.model.Lnfsdxq;
import com.ceeses.model.Lnyxlqqk;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/4.
 */
public interface LnfsdxqDao {

    /**
     * @param lnfsdxqs 历年分数段详情
     */
    void initLnfsdxq(List<Lnfsdxq> lnfsdxqs);


    Lnfsdxq queryTotalByGrade(Integer year, Integer grade);

    Integer queryCountByGrade(Lnfsdxq lnfsdxq);

}
