package com.ceeses.service;

import com.ceeses.dao.QueryRecordDao;
import com.ceeses.model.QueryRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 查询记录服务
 *
 * @author deng.zhang
 * @since 1.0 created on 2017/6/24
 */
@Service
public class QueryRecordService {
    @Autowired
    private QueryRecordDao queryRecordDao;

    /**
     * 保存查询记录
     *
     * @param queryRecord 查询记录
     */
    public void save(QueryRecord queryRecord) {
        queryRecordDao.save(queryRecord);
    }
}
