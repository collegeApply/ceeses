package com.ceeses.dao.mapper;

import com.ceeses.model.CollegeInfo;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/6.
 */
public interface CollegeInfoMapper {
    /**
     * 批量保存院校信息
     *
     * @param collegeInfos 院校信息
     */
    void batchSaveCollegeInfo(List<CollegeInfo> collegeInfos);

    /**
     * 清空表数据
     */
    void empty();

    /**
     * 根据省市查询院校信息
     *
     * @param area 省市名称
     * @return 院校信息列表
     */
    List<CollegeInfo> findByArea(String area);
}
