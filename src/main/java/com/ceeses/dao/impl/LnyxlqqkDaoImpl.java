package com.ceeses.dao.impl;

import com.ceeses.dao.LnyxlqqkDao;
import com.ceeses.dao.mapper.LnyxlqqkMapper;
import com.ceeses.dto.VolunteerInfo;
import com.ceeses.dto.VolunteerRequest;
import com.ceeses.model.Lnyxlqqk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/2
 */
@Repository
public class LnyxlqqkDaoImpl implements LnyxlqqkDao {
    @Autowired
    private LnyxlqqkMapper lnyxlqqkMapper;

    @Override
    public void bulkSave(List<Lnyxlqqk> lnyxlqqks) {
        if (lnyxlqqks != null && lnyxlqqks.size() > 0) {
            lnyxlqqkMapper.bulkSave(lnyxlqqks);
        }
    }

    @Override
    public List<Lnyxlqqk> query(Lnyxlqqk lnyxlqqk) {
        return lnyxlqqkMapper.query(lnyxlqqk);
    }

    @Override
    public List<VolunteerInfo> queryVolunteerInfo(VolunteerRequest volunteerRequest) {
        return lnyxlqqkMapper.queryVolunteerInfo(volunteerRequest);
    }

    @Override
    public List<VolunteerInfo> queryVolunteerInfoWithMajor(VolunteerRequest volunteerRequest) {
        return lnyxlqqkMapper.queryVolunteerInfoWithMajor(volunteerRequest);
    }
}
