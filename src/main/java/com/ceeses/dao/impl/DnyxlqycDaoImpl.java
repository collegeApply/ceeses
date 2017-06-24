package com.ceeses.dao.impl;

import com.ceeses.dao.DnyxlqycDao;
import com.ceeses.dao.mapper.DnyxlqycMapper;
import com.ceeses.dto.CollegeEnrollYc;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Dnyxlqyc;
import com.ceeses.model.Dnzylqyc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/24.
 */
@Repository
public class DnyxlqycDaoImpl implements DnyxlqycDao {

    @Autowired
    DnyxlqycMapper dnyxlqycMapper;

    @Override
    public void batchSaveDnyxlqyc(List<Dnyxlqyc> dnyxlqycs) {
        dnyxlqycMapper.batchSaveDnyxlqyc(dnyxlqycs);
    }

    @Override
    public void batchSaveDnzylqyc(List<Dnzylqyc> dnzylqycs) {
        dnyxlqycMapper.batchSaveDnzylqyc(dnzylqycs);
    }

    @Override
    public List<CollegeEnrollYc> queryCollegeYc(Integer year){
        return dnyxlqycMapper.queryCollegeYc(year);
    }
}
