package com.ceeses.dao.impl;

import com.ceeses.dao.CollegeInfoDao;
import com.ceeses.dao.mapper.CollegeInfoMapper;
import com.ceeses.model.CollegeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/6.
 */
@Repository
public class CollegeInfoDaoImpl implements CollegeInfoDao {

    @Autowired
    CollegeInfoMapper collegeInfoMapper;

    @Override
    public void batchSaveCollegeInfo(List<CollegeInfo> collegeInfos) {
        collegeInfoMapper.batchSaveCollegeInfo(collegeInfos);
    }

    @Override
    public void empty() {
        collegeInfoMapper.empty();
    }
}
