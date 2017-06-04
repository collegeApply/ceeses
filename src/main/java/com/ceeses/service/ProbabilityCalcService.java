package com.ceeses.service;



import com.ceeses.dto.ProbabilityCalaResponse;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Lnskfsx;
import com.ceeses.model.Lnyxlqtj;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/3.
 */
public class ProbabilityCalcService {

    /**
     * 根据年份，成绩，科类计算能达到的批次，只计算需要预估的年份比如2017
     * @param year
     * @param grade
     * @return
     */
    private Integer getBatch(Integer year, Integer grade, Integer category){
        //查询省控线
        Lnskfsx cutoffScoreHistory = new Lnskfsx();
        return 0;
    }

    /**
     * 这里的批次只有本科一批二批三批专科等
     * 根据年份，批次，科类，当年名次 + 分数段表，首先得出达到这个科类的人数
     * 根据某年科类人数，当年名次，计算出历年映射的名次
     */
    private Integer getTotalRanking(Integer year ,Integer batch, Integer category, Integer ranking){
        return 0;
    }


    private List<Lnyxlqtj> queryAllColleges() {
        return  null;
    }

    /**
     * 根据历年映射的名次，得出满足条件的院校
     */
    public ProbabilityCalaResponse getTargetColleges(ProbabilityCalcRequest probabilityCalcRequest){
        //默认只模拟计算15年之后的数据
        if (probabilityCalcRequest.getYear() < 2015) {
            return null;
        }

        Integer currentYear = probabilityCalcRequest.getYear();
        Integer batch = this.getBatch(currentYear, probabilityCalcRequest.getGrade(),
                probabilityCalcRequest.getCategory());


        for (Integer yearIndex = currentYear - 1; yearIndex >= currentYear -5 ;yearIndex -- ){
            //开始计算映射至 某年的排名
            Integer yearRanking =  getTotalRanking(yearIndex, batch,
                    probabilityCalcRequest.getCategory(),probabilityCalcRequest.getRanking());

            //查询当前排名在某年满足规则的院校



        }


        return null;
    }


}
