package com.ceeses.dto;

/**
 * Created by zhaoshan on 2017/6/11.
 */
public enum BatchInfoEnum {

    // 2016 1.提前本科   2.第一批本科   3.第二批本科   4.第三批本科   5.省内预科   6.提前专科
    // 7.普通专科   8.省内本科定向   9.省内专科定向   A.提前本科定向   B.一本定向   C.二本定向
    // D.三本定向   E.提前专科定向   F.普通专科定向   H.贫困地区定向   Z.自主招生

    // 2015 1.提前本科   2.第一批本科   3.第二批本科   4.第三批本科   5.省内预科   6.提前专科
    // 7.普通专科   8.省内本科定向   9.省内专科定向   A.提前本科定向   B.一本定向   C.二本定向
    // D.三本定向   E.提前专科定向   F.普通


    // 2014 1.提前本科   2.第一批本科   3.第二批本科   4.第三批本科   5.省内预科   6.提前专科
    // 7.普通专科   8.省内本科定向   9.省内专科定向   A.提前本科定向   B.一本定向   C.二本定向
    // D.三本定向   E.提前专科定向   F.普通专科定向

    // 2013 1.提前本科   2.第一批本科   3.第二批本科   4.第三批本科   5.省内预科   6.提前专科
    // 7.普通专科   8.省内本科定向   9.省内专科定向   A.提前本科定向   B.一本定向   C.二本定向
    // D.三本定向   E.提前专科定向   F.普通专科定向

    BATCH_1("1","提前本科"),
    BATCH_2("2","第一批本科"),
    BATCH_3("3","第二批本科"),
    BATCH_4("4","第三批本科"),
    BATCH_5("5","省内预科"),
    BATCH_6("6","提前专科"),
    BATCH_7("7","普通专科"),
    BATCH_8("8","省内本科定向"),
    BATCH_9("9","省内专科定向"),
    BATCH_A("A","提前本科定向"),
    BATCH_C("B","一本定向"),
    BATCH_B("C","二本定向"),
    BATCH_D("D","三本定向"),
    BATCH_E("E","提前专科定向"),
    BATCH_F("F","普通专科定向"),
    BATCH_H("H","贫困定向"),;

    public String key;

    public String value;

    private BatchInfoEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public static String getNameByKey(String key){
        for (BatchInfoEnum infoEnum:BatchInfoEnum.values()){
            if (infoEnum.key.equals(key)){
                return infoEnum.value;
            }
        }
        return null;
    }

}
