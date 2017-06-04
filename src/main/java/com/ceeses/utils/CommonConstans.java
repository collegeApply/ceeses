package com.ceeses.utils;

import com.ceeses.model.Lnskfsx;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/4.
 */
@Component
public class CommonConstans {



    //存储初始化的历年省控线
    public  static final Map<String, Lnskfsx> lnskfsxMap = new HashMap<>();



    public void initLnskfsxMap(){

    }
}
