package com.ceeses.dao;

import com.ceeses.dto.CollegeEnrollHistory;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Lnyxlqtj;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/6.
 */
public interface LnyxlqtjDao {

    /**
     * 保存历年院校录取统计信息
     *
     * @param lnyxlqtjs
     */
    void batchSaveLnyxlqtj(List<Lnyxlqtj> lnyxlqtjs);

    /**
     * 修改历年院校录取统计信息
     *
     * @param lnyxlqtj 历年院校录取统计信息
     */
    void update(Lnyxlqtj lnyxlqtj);

    /**
     * 分页查询历年院校录取统计
     *
     * @param params 参数
     * @return 历年院校录取统计
     */
    List<Lnyxlqtj> queryByPage(Map<String, Object> params);

    /**
     * 查询总数
     *
     * @return 总数
     */
    int count();

    /**
     * 思考实现筛选出满足条件的院校列表
     * 根据年份，批次，科类，加上固定的专业和学校的条件来筛选
     * 需要多次查询；
     *
     * @param probabilityCalcRequest
     * @return
     */
    List<CollegeEnrollHistory> queryCollegeEnrollHistory(ProbabilityCalcRequest probabilityCalcRequest);
}
