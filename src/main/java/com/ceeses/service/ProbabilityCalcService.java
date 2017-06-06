package com.ceeses.service;



import com.ceeses.dao.LnfsdxqDao;
import com.ceeses.dto.ProbabilityCalaResponse;
import com.ceeses.dto.ProbabilityCalcRequest;
import com.ceeses.model.Lnfsdxq;
import com.ceeses.model.Lnskfsx;
import com.ceeses.model.Lnyxlqtj;
import com.ceeses.utils.CommonConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.util.*;

/**
 * Created by zhaoshan on 2017/6/3.
 */
@Component
public class ProbabilityCalcService {

    @Autowired
    LnfsdxqDao lnfsdxqDao;

    /**
     * 根据年份，成绩，科类计算能达到的批次，只计算需要预估的年份比如2017
     * 返回当前批次对应的分数线
     * @param year
     * @param grade
     * @return
     */
    private String getBatch(Integer year, Integer grade, String category){
        String batchResult = "";
        //倒序
        TreeSet sortedSet = new TreeSet(new Comparator() {
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

        //查询省控线
        for (Lnskfsx lnskfsx : CommonConstans.lnskfsxMap.values()){
            if (lnskfsx.getYear() != year || !lnskfsx.getCategory().equals(category)){
                continue;
            }
            sortedSet.add(lnskfsx);
        }

        for (Object obj : sortedSet){
            Lnskfsx hg = (Lnskfsx) obj;
            //拿到的第一个小于你当前分数的就是你分数对应的批次线
            if (hg.getGrade() < grade){
                batchResult = hg.getBatch();
            }
        }
        return batchResult;
    }

    public static void main(String[] args) {
        SortedSet sortedSet = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Integer h1 = (Integer) o1;
                Integer h2 = (Integer) o2;
                if (h1 > h2) {
                    return -1;
                }
                if (h1 < h2) {
                    return 1;
                }
                return 0;
            }
        });
        sortedSet.add(123);
        sortedSet.add(18);
        sortedSet.add(122);
        sortedSet.add(135);
        sortedSet.add(128);
        for (Object obj : sortedSet){
            System.out.println(obj);
        }
    }

    /**
     * 这里的批次只有本科一批二批三批专科等
     * 根据年份，批次，科类，当年名次 + 分数段表，首先得出达到这个科类的人数
     * 根据某年科类人数，当年名次，计算出历年映射的名次
     */
    private Integer getTotalRanking(Integer year, Integer grade){
        Lnfsdxq lnfsdxq = new Lnfsdxq();
        lnfsdxq.setYear(year);
        lnfsdxq.setTotalGrade(grade);
        //对应年份达到某个批次的总人数
        Integer total = lnfsdxqDao.queryCountByGrade(lnfsdxq);
        return 0;
    }


    private List<Lnyxlqtj> queryAllColleges() {



        return  null;
    }

    /**
     * 根据历年映射的名次，得出满足条件的院校
     *
     */
    public ProbabilityCalaResponse getTargetColleges(ProbabilityCalcRequest probabilityCalcRequest){
        //默认只模拟计算15年之后的数据
        if (probabilityCalcRequest.getYear() < 2015) {
            return null;
        }

        Integer currentYear = probabilityCalcRequest.getYear();
        String batch = this.getBatch(currentYear, probabilityCalcRequest.getGrade(),
                probabilityCalcRequest.getCategory());

        //当年批次分数，达到这个批次的总人数
        Integer currentGrade = CommonConstans.lnskfsxMap.get(currentYear + "_" + batch + "_" +
                probabilityCalcRequest.getCategory()).getGrade();
        Integer currentTotal = getTotalRanking(currentYear,currentGrade);

        for (Integer yearIndex = currentYear - 1; yearIndex >= currentYear -5 ;yearIndex -- ){

            //首先拿到某年固定批次的分数
            Integer batchGrade = CommonConstans.lnskfsxMap.get(yearIndex + "_" + batch + "_" +
                    probabilityCalcRequest.getCategory()).getGrade();
            Integer indexTotal = getTotalRanking(yearIndex,batchGrade);

            //开始计算映射至某年的排名
            Double indexRanking = (double)(probabilityCalcRequest.getRanking() * currentTotal / indexTotal);

            //根据映射的排名查出院校
//            Integer yearRanking =  getTotalRanking(yearIndex, batch,
//                    probabilityCalcRequest.getCategory(),probabilityCalcRequest.getRanking());

            //查询当前排名在某年满足规则的院校



        }


        return null;
    }


}
