package com.ceeses.utils;

import com.ceeses.model.Lnskfsx;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoshan on 2017/6/4.
 */
public class CommonConstans {


    //存储初始化的历年省控线
    public  static final Map<String, Lnskfsx> lnskfsxMap = new HashMap<>();


    // 批次如下：
    // 1.提前本科   2.第一批本科   3.第二批本科   4.第三批本科   5.省内预科
    // 6.提前专科   7.普通专科   8.省内本科定向   9.省内专科定向   A.提前本科定向
    // B.一本定向   C.二本定向   D.三本定向   E.提前专科定向   F.普通专科定向
    // H.贫困地区定向   Z.自主招生

    // 省控线数据如下：
    // 1-5分别代表:本科一批，本科二批，本科三批，专科一批，专科二批
    public static final Map<String,String> batchAndSkxMap = new HashMap<>();

    static {

        batchAndSkxMap.put("1","1");



    }

    /**
     * 据年份，分数查询出控制线，用控制线计算线差
     * 这是个蛋疼的方法。。。
     * @param year
     * @param grade
     * @param batchCode
     * @return
     */
    public  static String getKzxByGrade(Integer year, Float grade, String batchCode){

        return "";
    }
}
