package com.ceeses.dao.mapper;

import com.ceeses.dto.VolunteerInfo;
import com.ceeses.dto.VolunteerRequest;
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

    /**
     * 查询符合条件的历年院校录取情况
     *
     * @param lnyxlqqk 查询条件
     * @return 历年院校录取情况
     */
    List<Lnyxlqqk> query(Lnyxlqqk lnyxlqqk);

    /**
     * 查询统计归并的志愿号数据
     * @param volunteerRequest
     * @return
     */
    public List<VolunteerInfo> queryVolunteerInfo(VolunteerRequest volunteerRequest);

    /**
     * 查询统计归并的志愿号数据,带专业数据
     * @param volunteerRequest
     * @return
     */
    public List<VolunteerInfo> queryVolunteerInfoWithMajor(VolunteerRequest volunteerRequest);
}
