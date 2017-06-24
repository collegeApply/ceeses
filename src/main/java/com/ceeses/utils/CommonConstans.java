package com.ceeses.utils;

import com.ceeses.model.Dnyxlqyc;
import com.ceeses.model.Dnzylqyc;
import com.ceeses.model.Lnskfsx;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by zhaoshan on 2017/6/4.
 */
public class CommonConstans {
    public static final Map<String, String> batchMap = new HashMap<>();

    //存储初始化的历年省控线
    public static final Map<String, Lnskfsx> lnskfsxMap = new HashMap<>();


    // 批次如下：
    // 1.提前本科   2.第一批本科   3.第二批本科   4.第三批本科   5.省内预科
    // 6.提前专科   7.普通专科   8.省内本科定向   9.省内专科定向   A.提前本科定向
    // B.一本定向   C.二本定向   D.三本定向   E.提前专科定向   F.普通专科定向
    // H.贫困地区定向   Z.自主招生

    // 省控线数据如下：
    // 1-5分别代表:本科一批，本科二批，本科三批，专科一批，专科二批
    //省控线增加：6.省内预科，7.贫困定向一本，8.贫困定向二本
    public static final Map<String, String> batchAndSkxMap = new HashMap<>();

    static {
        batchMap.put("1", "提前本科");
        batchMap.put("2", "第一批本科");
        batchMap.put("3", "第二批本科");
        batchMap.put("4", "第三批本科");
        batchMap.put("5", "省内预科");
        batchMap.put("6", "提前专科");
        batchMap.put("7", "普通专科");
        batchMap.put("8", "省内本科定向");
        batchMap.put("9", "省内专科定向");
        batchMap.put("A", "提前本科定向");
        batchMap.put("B", "一本定向");
        batchMap.put("C", "二本定向");
        batchMap.put("D", "三本定向");
        batchMap.put("E", "提前专科定向");
        //key是批次，value是省控线值
        //1,8,A,H,Z需特殊处理
        batchAndSkxMap.put("2", "1");
        batchAndSkxMap.put("3", "2");
        batchAndSkxMap.put("4", "3");
        batchAndSkxMap.put("5", "6");
        batchAndSkxMap.put("6", "4");
        batchAndSkxMap.put("7", "4");
        batchAndSkxMap.put("9", "4");
        batchAndSkxMap.put("B", "1");
        batchAndSkxMap.put("C", "2");
        batchAndSkxMap.put("D", "3");
        batchAndSkxMap.put("E", "4");
        batchAndSkxMap.put("F", "4");
    }

    /**
     * 据年份，批次。分数查询出控制线，用控制线计算线差
     * 这是个蛋疼的方法。。。
     *
     * @param year
     * @param grade
     * @param batchCode
     * @param category
     * @return
     */
    public static String getBatchByGrade(Integer year, Integer grade, String batchCode, String category){

        //自主招生
        if ("Z".equals(batchCode)){
            return "0";
        }

        if (!"1".equals(batchCode) && !"8".equals(batchCode) && !"A".equals(batchCode) &&
                !"H".equals(batchCode)) {
            String kzxCode = batchAndSkxMap.get(batchCode);
            if (lnskfsxMap.containsKey(year + "_" + kzxCode + "_" + category)){
                return lnskfsxMap.get(year + "_" + kzxCode + "_" + category).getBatch();
            } else {
                return "0";
            }
        }

        TreeSet<Lnskfsx> sortedSet = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Lnskfsx h1 = (Lnskfsx) o1;
                Lnskfsx h2 = (Lnskfsx) o2;
                if (h1.getGrade() > h2.getGrade()) {
                    return -1;
                }
                if (h1.getGrade() < h2.getGrade()) {
                    return 1;
                }
                return 0;
            }
        });
        //这三种特殊情况时候
        if ("1".equals(batchCode) || "8".equals(batchCode) || "A".equals(batchCode)) {
            for (Lnskfsx lnskfsx : CommonConstans.lnskfsxMap.values()) {
                if (lnskfsx.getYear().equals(year) && lnskfsx.getCategory().equals(category) &&
                        !"7".equals(lnskfsx.getCategory()) && !"8".equals(lnskfsx.getCategory())) {
                    sortedSet.add(lnskfsx);
                }
            }
            for (Lnskfsx fxs : sortedSet) {
                //拿到的第一个小于你当前分数的就是你分数对应的批次线
                if (fxs.getGrade() < grade){
                    return fxs.getBatch();
                }
            }
        }
        sortedSet.clear();

        //贫困定向这种特殊情况时候处理,之排序处理7,8两个分数线，分别对应贫困定向的一本和二本
        if ("H".equals(batchCode)) {
            for (Lnskfsx lnskfsx : CommonConstans.lnskfsxMap.values()) {
                if (lnskfsx.getYear().equals(year) && lnskfsx.getCategory().equals(category) &&
                        ("7".equals(lnskfsx.getCategory()) || "8".equals(lnskfsx.getCategory()))) {
                    sortedSet.add(lnskfsx);
                }
            }
            for (Lnskfsx fxs : sortedSet) {
                //拿到的第一个小于你当前分数的就是你分数对应的批次线
                if (fxs.getGrade() < grade){
                    return fxs.getBatch();
                }
            }
        }

        return "0";
    }


    public static Map<String,String> volunteerMap = new HashMap<>();

    public static Map<String,String> volunteerMajorMap = new HashMap<>();

    public static Map<String,Dnyxlqyc> dnyxlqycMap = new HashMap<>();

    public static Map<String,Dnzylqyc> dnzylqycMap = new HashMap<>();


    public static float getFsxGrade(Integer yearIndex,String fsxBatch, String category){

        if (!CommonConstans.lnskfsxMap.containsKey(yearIndex + "_" +
                fsxBatch + "_" + category)){
            return 0.0f;
        }
        return (float)CommonConstans.lnskfsxMap.get(yearIndex + "_" +
                fsxBatch + "_" + category).getGrade();
    }
}
