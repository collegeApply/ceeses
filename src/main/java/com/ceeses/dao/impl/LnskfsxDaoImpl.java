package com.ceeses.dao.impl;

import com.ceeses.dao.LnskfsxDao;
import com.ceeses.dao.mapper.LnskfsxMapper;
import com.ceeses.model.Lnskfsx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/4.
 */
@Repository
public class LnskfsxDaoImpl implements LnskfsxDao {

    @Autowired
    private LnskfsxMapper lnskfsxMapper;

    @Override
    public List<Lnskfsx> queryLnskfsx(Integer year) {

        return lnskfsxMapper.queryLnskfsx(year);
    }


}
