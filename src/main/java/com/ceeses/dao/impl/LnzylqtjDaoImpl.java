package com.ceeses.dao.impl;

import com.ceeses.dao.LnzylqtjDao;
import com.ceeses.dao.mapper.LnzylqtjMapper;
import com.ceeses.model.Lnzylqtj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/6.
 */
@Repository
public class LnzylqtjDaoImpl implements LnzylqtjDao {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LnzylqtjDaoImpl.class);

    @Autowired
    LnzylqtjMapper lnzylqtjMapper;

    @Override
    public void batchSaveLnzylqtj(List<Lnzylqtj> lnzylqtjs) {
        try {
            lnzylqtjMapper.batchSaveLnzylqtj(lnzylqtjs);
        } catch (Exception e) {
            LOGGER.error("保存历年专业录取统计失败, {}", lnzylqtjs, e);
        }
    }

    @Override
    public void update(Lnzylqtj lnzylqtj) {
        lnzylqtjMapper.update(lnzylqtj);
    }

    @Override
    public List<Lnzylqtj> queryByPage(Map<String, Object> params) {
        return lnzylqtjMapper.queryByPage(params);
    }

    @Override
    public int count() {
        return lnzylqtjMapper.count();
    }

    @Override
    public void empty() {
        lnzylqtjMapper.empty();
    }
}
