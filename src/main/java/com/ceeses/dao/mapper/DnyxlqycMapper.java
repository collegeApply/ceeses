package com.ceeses.dao.mapper;

import com.ceeses.dto.CollegeEnrollYc;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Dnyxlqyc;
import com.ceeses.model.Dnzylqyc;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/24.
 */
public interface DnyxlqycMapper {

    /**
     * 批量保存当年院校预测数据
     * @param dnyxlqycs
     */
    void batchSaveDnyxlqyc(List<Dnyxlqyc> dnyxlqycs);

    /**
     * 批量保存当年专业预测数据
     * @param dnzylqycs
     */
    void batchSaveDnzylqyc(List<Dnzylqyc> dnzylqycs);

    /**
     * 查询预测的院校数据
     * @param year
     * @return
     */
    List<CollegeEnrollYc> queryCollegeYc(Integer year);
}
