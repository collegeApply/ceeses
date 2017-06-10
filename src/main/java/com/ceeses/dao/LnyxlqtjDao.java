package com.ceeses.dao;

import com.ceeses.dto.CollegeEnrollHistory;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Lnyxlqtj;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/6.
 */
public interface LnyxlqtjDao {

    /**
     * 保存历年院校录取统计信息
     * @param lnyxlqtjs
     */
    void batchSaveLnyxlqtj(List<Lnyxlqtj> lnyxlqtjs);


    /**
     * 思考实现筛选出满足条件的院校列表
     * 根据年份，批次，科类，加上固定的专业和学校的条件来筛选
     * 需要多次查询；
     * @param probabilityCalcRequest
     * @return
     */
    List<CollegeEnrollHistory> queryCollegeEnrollHistory(ProbabilityCalcRequest probabilityCalcRequest);
}
