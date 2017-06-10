package com.ceeses.dao.mapper;

import com.ceeses.dto.CollegeEnrollHistory;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Lnyxlqtj;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/6.
 *
 */
public interface LnyxlqtjMapper {

    /**
     * 保存历年院校录取统计
     * @param lnyxlqtjs
     */
    void batchSaveLnyxlqtj(List<Lnyxlqtj> lnyxlqtjs);

    /**
     * 查询历年院校录取历史
     * @param probabilityCalcRequest
     * @return
     */
    public List<CollegeEnrollHistory> queryCollegeEnrollHistory(ProbabilityCalcRequest probabilityCalcRequest);
}