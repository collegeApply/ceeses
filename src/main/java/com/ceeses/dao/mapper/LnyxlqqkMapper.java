package com.ceeses.dao.mapper;

import com.ceeses.model.Lnyxlqqk;

import java.util.List;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/2
 */
public interface LnyxlqqkMapper {
    /**
     * 批量保存历年院校录取情况
     *
     * @param lnyxlqqks 历年院校录取情况列表
     */
    void bulkSave(List<Lnyxlqqk> lnyxlqqks);
}
