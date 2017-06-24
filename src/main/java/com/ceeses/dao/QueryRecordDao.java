package com.ceeses.dao;

import com.ceeses.model.QueryRecord;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/24
 */
public interface QueryRecordDao {
    /**
     * 保存查询记录
     *
     * @param queryRecord 查询记录
     */
    void save(QueryRecord queryRecord);
}
