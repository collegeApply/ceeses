package com.ceeses.dao;

import com.ceeses.model.Lnzylqtj;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/6.
 */
public interface LnzylqtjDao {

    /**
     * 批量保存历年专业录取情况，需关联于历年院校录取情况
     * @param lnzylqtjs
     */
    void batchSaveLnzylqtj(List<Lnzylqtj> lnzylqtjs);

}
