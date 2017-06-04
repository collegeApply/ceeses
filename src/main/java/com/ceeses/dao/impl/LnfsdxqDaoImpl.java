package com.ceeses.dao.impl;

import com.ceeses.dao.LnfsdxqDao;
import com.ceeses.dao.mapper.LnfsdxqMapper;
import com.ceeses.dao.mapper.LnyxlqqkMapper;
import com.ceeses.model.Lnfsdxq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/4.
 */
@Repository
public class LnfsdxqDaoImpl implements LnfsdxqDao {

    @Autowired
    private LnfsdxqMapper lnfsdxqMapper;

    @Override
    public void initLnfsdxq(List<Lnfsdxq> lnfsdxqs) {
        if (lnfsdxqs != null && lnfsdxqs.size() > 0) {
            lnfsdxqMapper.initLnfsdxq(lnfsdxqs);
        }
    }

    @Override
    public Lnfsdxq queryTotalByGrade(Integer year, Integer grade) {
        Lnfsdxq result = lnfsdxqMapper.queryTotalByGrade(year,grade);

        return result;
    }

    @Override
    public Integer queryCountByGrade(Lnfsdxq lnfsdxq) {
        return lnfsdxqMapper.queryCountByGrade(lnfsdxq);
    }


}
