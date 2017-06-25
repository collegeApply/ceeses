package com.ceeses.dao.impl;

import com.ceeses.dao.LnyxlqtjDao;
import com.ceeses.dao.mapper.LnyxlqtjMapper;
import com.ceeses.dto.CollegeEnrollHistory;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Lnyxlqtj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/6.
 */
@Repository
public class LnyxlqtjDaoImpl implements LnyxlqtjDao {
    @Autowired
    private LnyxlqtjMapper lnyxlqtjMapper;

    @Override
    public void batchSaveLnyxlqtj(List<Lnyxlqtj> lnyxlqtjs) {
        lnyxlqtjMapper.batchSaveLnyxlqtj(lnyxlqtjs);
    }

    @Override
    public void update(Lnyxlqtj lnyxlqtj) {
        lnyxlqtjMapper.update(lnyxlqtj);
    }

    @Override
    public List<Lnyxlqtj> queryByPage(Map<String, Object> params) {
        return lnyxlqtjMapper.queryByPage(params);
    }

    @Override
    public int count() {
        return lnyxlqtjMapper.count();
    }

    @Override
    public void empty() {
        lnyxlqtjMapper.empty();
    }

    @Override
    public List<CollegeEnrollHistory> queryCollegeEnrollHistory(ProbabilityCalcRequest probabilityCalcRequest) {
        return lnyxlqtjMapper.queryCollegeEnrollHistory(probabilityCalcRequest);
    }
}
