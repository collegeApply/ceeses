package com.ceeses.dao;

import com.ceeses.model.Lnzylqtj;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/6.
 */
public interface LnzylqtjDao {

    /**
     * 批量保存历年专业录取情况，需关联于历年院校录取情况
     *
     * @param lnzylqtjs
     */
    void batchSaveLnzylqtj(List<Lnzylqtj> lnzylqtjs);

    /**
     * 修改历年专业录取统计信息
     *
     * @param lnzylqtj 历年专业录取统计信息
     */
    void update(Lnzylqtj lnzylqtj);

    /**
     * 分页查询历年专业录取统计
     *
     * @param params 参数
     * @return 历年专业录取统计
     */
    List<Lnzylqtj> queryByPage(Map<String, Object> params);

    /**
     * 查询总数
     *
     * @return 总数
     */
    int count();
}
