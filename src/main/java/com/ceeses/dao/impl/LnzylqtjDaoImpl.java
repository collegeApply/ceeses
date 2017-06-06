package com.ceeses.dao.impl;

import com.ceeses.dao.LnzylqtjDao;
import com.ceeses.dao.mapper.LnzylqtjMapper;
import com.ceeses.model.Lnzylqtj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/6.
 */
@Repository
public class LnzylqtjDaoImpl implements LnzylqtjDao{

    @Autowired
    LnzylqtjMapper lnzylqtjMapper;

    @Override
    public void batchSaveLnzylqtj(List<Lnzylqtj> lnzylqtjs) {
        lnzylqtjMapper.batchSaveLnzylqtj(lnzylqtjs);
    }
}
