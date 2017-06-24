package com.ceeses.dao;

import com.ceeses.dto.CollegeEnrollYc;
import com.ceeses.model.Dnyxlqyc;
import com.ceeses.model.Dnzylqyc;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/24.
 */
public interface DnyxlqycDao {

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
     * 查询当年的院校招录预测数据
     * @param year
     * @return
     */
    List<CollegeEnrollYc> queryCollegeYc(Integer year);
}
