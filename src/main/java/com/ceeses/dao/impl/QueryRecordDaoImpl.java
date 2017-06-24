package com.ceeses.dao.impl;

import com.ceeses.dao.QueryRecordDao;
import com.ceeses.dao.mapper.QueryRecordMapper;
import com.ceeses.model.QueryRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/24
 */
@Repository
public class QueryRecordDaoImpl implements QueryRecordDao {
    @Autowired
    private QueryRecordMapper queryRecordMapper;

    @Override
    public void save(QueryRecord queryRecord) {
        queryRecordMapper.save(queryRecord);
    }
}
