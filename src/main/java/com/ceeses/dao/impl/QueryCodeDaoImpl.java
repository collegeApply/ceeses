package com.ceeses.dao.impl;

import com.ceeses.dao.QueryCodeDao;
import com.ceeses.dao.mapper.QueryCodeMapper;
import com.ceeses.model.QueryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-06-29 20:53
 */
@Repository
public class QueryCodeDaoImpl implements QueryCodeDao {
    /**
     * 查询码Mapper
     */
    @Autowired
    private QueryCodeMapper queryCodeMapper;

    @Override
    public void save(QueryCode queryCode) {
        queryCodeMapper.save(queryCode);
    }

    @Override
    public QueryCode queryByCode(String code) {
        return queryCodeMapper.queryByCode(code);
    }
}
