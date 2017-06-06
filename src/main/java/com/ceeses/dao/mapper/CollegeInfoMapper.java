package com.ceeses.dao.mapper;

import com.ceeses.model.CollegeInfo;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/6.
 */
public interface CollegeInfoMapper {
    /**
     * 批量保存院校信息
     * @param collegeInfos
     */
    void batchSaveCollegeInfo(List<CollegeInfo> collegeInfos);
}
