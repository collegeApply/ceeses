package com.ceeses.service;

import com.ceeses.dao.QueryCodeDao;
import com.ceeses.model.QueryCode;
import com.ceeses.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-06-29 20:54
 */
@Service
public class QueryCodeService {
    /**
     * 查询码DAO
     */
    @Autowired
    private QueryCodeDao queryCodeDao;

    /**
     * 随机生成查询码
     */
    public synchronized void randomGenerateCode() throws Exception {
        for (int i = 0; i < 8; i++) {
            QueryCode queryCode = new QueryCode();
            queryCode.setCode(Md5Util.md5(String.valueOf(System.currentTimeMillis()) + i));
            queryCodeDao.save(queryCode);
        }
    }

    public QueryCode queryByCode(String code) {
        return queryCodeDao.queryByCode(code);
    }
}
