package com.ceeses.dao;

import com.ceeses.model.Lnskfsx;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/4.
 */
public interface LnskfsxDao {

    /**
     * 直接用sql执行初始化,转换成map
     * 参考CommonConstans
     * 17年数据出来收手动插入数据库
     */
    List<Lnskfsx> queryLnskfsx(Integer year);

}
