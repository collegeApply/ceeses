package com.ceeses.dao.mapper;

import com.ceeses.model.QueryCode;

/**
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-06-29 20:44
 */
public interface QueryCodeMapper {
    /**
     * 保存查询码信息
     *
     * @param queryCode 查询码信息
     */
    void save(QueryCode queryCode);

    /**
     * 根据查询码查询查询码信息
     *
     * @param code 查询码
     * @return 查询码信息
     */
    QueryCode queryByCode(String code);
}
