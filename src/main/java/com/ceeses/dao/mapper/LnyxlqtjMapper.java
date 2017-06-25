package com.ceeses.dao.mapper;

import com.ceeses.dto.CollegeEnrollHistory;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Lnyxlqtj;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/6.
 */
public interface LnyxlqtjMapper {

    /**
     * 保存历年院校录取统计
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
     * 清空表数据
     */
    void empty();

    /**
     * 查询历年院校录取历史
     *
     * @param probabilityCalcRequest
     * @return
     */
    public List<CollegeEnrollHistory> queryCollegeEnrollHistory(ProbabilityCalcRequest probabilityCalcRequest);
}
