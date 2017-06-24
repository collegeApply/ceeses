package com.ceeses.dao;

import com.ceeses.dto.VolunteerInfo;
import com.ceeses.dto.VolunteerRequest;
import com.ceeses.model.Lnyxlqqk;

import java.util.List;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/2
 */
public interface LnyxlqqkDao {
    /**
     * 批量保存历年院校录取情况
     *
     * @param lnyxlqqks 历年院校录取情况列表
     */
    void bulkSave(List<Lnyxlqqk> lnyxlqqks);

    /**
     * 查询符合条件的历年院校录取情况
     *
     * @param lnyxlqqk 查询条件
     * @return 历年院校录取情况
     */
    List<Lnyxlqqk> query(Lnyxlqqk lnyxlqqk);

    /**
     * 查询统计归并历年院校录取志愿号情况
     * @param volunteerRequest
     * @return
     */
    List<VolunteerInfo> queryVolunteerInfo(VolunteerRequest volunteerRequest);


    /**
     * 查询统计归并历年院校录取志愿号情况
     * @param volunteerRequest
     * @return
     */
    List<VolunteerInfo> queryVolunteerInfoWithMajor(VolunteerRequest volunteerRequest);

}
