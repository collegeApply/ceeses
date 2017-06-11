package com.ceeses.service;



import com.ceeses.dao.LnfsdxqDao;
import com.ceeses.dao.LnyxlqtjDao;
import com.ceeses.dto.*;
import com.ceeses.extractor.LnyxlqqkExtractor;
import com.ceeses.model.Lnfsdxq;
import com.ceeses.model.Lnskfsx;
import com.ceeses.model.Lnyxlqtj;
import com.ceeses.utils.CommonConstans;
import com.sun.org.apache.bcel.internal.generic.LLOAD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.Inet4Address;
import java.util.*;

/**
 * Created by zhaoshan on 2017/6/3.
 */
@Component
public class ProbabilityCalcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProbabilityCalcService.class);


    @Autowired
    LnfsdxqDao lnfsdxqDao;

    @Autowired
    LnyxlqtjDao lnyxlqtjDao;

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

        if (category.equals("文史")){
            category = "1";
        } else if (category.equals("理工")){
            category = "5";
        }

        //查询省控线
        for (Lnskfsx lnskfsx : CommonConstans.lnskfsxMap.values()){
            if (lnskfsx.getYear().equals(year)  && lnskfsx.getCategory().equals(category)){
                sortedSet.add(lnskfsx);
            }
        }

        for (Object obj : sortedSet){
            Lnskfsx hg = (Lnskfsx) obj;
            //拿到的第一个小于你当前分数的就是你分数对应的批次线
            if (hg.getGrade() < grade){
                batchResult = hg.getBatch();
                break;
            }
        }
        return batchResult;
    }

    /**
     * 这里的批次只有本科一批二批三批专科等
     * 根据年份，批次，科类，当年名次 + 分数段表，首先得出达到这个科类的人数
     * 根据某年科类人数，当年名次，计算出历年映射的名次
     */
    private Integer getTotalRanking(Integer year, Integer grade, String category){
        Lnfsdxq lnfsdxq = new Lnfsdxq();
        lnfsdxq.setYear(year);
        lnfsdxq.setTotalGrade(grade);
        if (category.equals("1")){
            lnfsdxq.setCategory("文科");
        }
        if (category.equals("5")){
            lnfsdxq.setCategory("理科");
        }
        //对应年份达到某个批次的总人数
        Integer total = lnfsdxqDao.queryCountByGrade(lnfsdxq);
        return total;
    }

    /**
     * 根据历年映射的名次，得出满足条件的院校
     *
     */
    public ProbabilityCalaResponse getTargetColleges(ProbabilityCalcRequest probabilityCalcRequest){

        ProbabilityCalaResponse response = new ProbabilityCalaResponse();

        //计算过去多少年的数据
        int calcYears = 3;

        //默认只模拟计算15年之后的数据
        if (probabilityCalcRequest.getYear() < 2015) {
            return null;
        }

        LOGGER.info("查询请求参数：" + probabilityCalcRequest.toString());
        //1.根据分数获取当年批次
        Integer currentYear = probabilityCalcRequest.getYear();
        String batch = this.getBatch(currentYear, probabilityCalcRequest.getGrade(),
                probabilityCalcRequest.getCategory());
        LOGGER.info("根据分数计算当前批次：{}" , batch);

        //科类转换，只处理文史理工两个科类
        String category = null;
        if (probabilityCalcRequest.getCategory().equals("文史")){
            category = "1";
        } else if (probabilityCalcRequest.getCategory().equals("理工")){
            category = "5";
        }

        //2.获取当年批次分数，计算达到这个批次的总人数
        Integer currentGrade = CommonConstans.lnskfsxMap.get(currentYear + "_" + batch + "_" +
                category).getGrade();
        Integer currentTotal = getTotalRanking(currentYear,currentGrade,category);

        LOGGER.info("当年该批次分数线：{}，通过此批次总人数{}" , currentGrade, currentTotal);

        //主键：年份+院校+批次+科类，专业录取统计主键：年份+院校+批次+科类+专业
        Map<String, Lnyxmc> lnyxmcMap = new HashMap<>();

        //总计需返回的院校列表,主键年份+院校+批次+科类
        //后续根据年份计算
        Map<String,ProbabilityCalaDTO> probabilityCalaDTOMap = new HashMap<>();

        //先存储所有历年出现过的专业的统计，主键院校+批次+科类+专业
        //后续根据年份计算
        Map<String,Lnzymc> allMajorMap = new HashMap<>();

        //以院校+批次+科类为单位的所有专业列表，对应于probabilityCalaDTOMap
        //院校历史路去过的专业都展示出来，计算非常麻烦
        Map<String,List<String>> schoolMajorMap = new HashMap<>();

        //3.开始组装每年的原始数据
        LOGGER.info("开始组装历年原始数据");
        for (Integer yearIndex = currentYear - 1; yearIndex >= currentYear - calcYears ;yearIndex -- ){

            //首先拿到某年固定批次的分数
            Integer batchGrade = CommonConstans.lnskfsxMap.get(yearIndex + "_" + batch + "_" +
                    category).getGrade();
            Integer indexTotal = getTotalRanking(yearIndex, batchGrade, category);

            LOGGER.info("年份：{} 分数线: {}, 通过分数线总人数：{}" ,yearIndex, batchGrade, indexTotal);

            //开始计算映射至某年的排名
            double indexRanking = (double)(probabilityCalcRequest.getRanking() * indexTotal / currentTotal);
            LOGGER.info("当年：{}名次{}，映射至{}年份，名次为{}", currentYear,
                    probabilityCalcRequest.getRanking(),yearIndex,indexRanking);

            //3.根据映射的排名查出院校初始数据
            ProbabilityCalcRequest queryTarget = new ProbabilityCalcRequest();
            BeanUtils.copyProperties(probabilityCalcRequest,queryTarget);
            queryTarget.setYear(yearIndex);
            queryTarget.setRanking((int)indexRanking);
            //TODO 需做批次转换，客户使用时候，统一转成16年的批次编号，输入参数转回当年，输出参数设置Map时候转回16年
            LOGGER.info("开始查询并初始化年份{}的录取数据", yearIndex);
            List<CollegeEnrollHistory> enrollHistories = lnyxlqtjDao.queryCollegeEnrollHistory(queryTarget);
            for (CollegeEnrollHistory enrollHistory :enrollHistories){

                //填充院校信息大Map
                if (!probabilityCalaDTOMap.keySet().contains(enrollHistory.getCollege_code() + "_" +
                        enrollHistory.getBatch_code() + "_" + enrollHistory.getCategory())){
                    ProbabilityCalaDTO calaDTO = new ProbabilityCalaDTO();
                    calaDTO.setCollegeCode(enrollHistory.getCollege_code());
                    calaDTO.setAreaName(enrollHistory.getArea());
                    calaDTO.setBatchCode(enrollHistory.getBatch_code());
                    calaDTO.setCollegeType(enrollHistory.getType());
                    calaDTO.setCategory(enrollHistory.getCategory());
                    calaDTO.setBatchName(BatchInfoEnum.getNameByKey(enrollHistory.getBatch_code()));
                    calaDTO.setCollegeName(enrollHistory.getCollege_name());
                    calaDTO.setCategory(enrollHistory.getCategory());
                    calaDTO.setCollegeRanking(enrollHistory.getRanking());
                    probabilityCalaDTOMap.put(enrollHistory.getCollege_code() + "_" +
                            enrollHistory.getBatch_code() + "_" + enrollHistory.getCategory(),calaDTO);
                }

                if (!lnyxmcMap.keySet().contains(yearIndex + "_" + enrollHistory.getCollege_code() + "_" +
                        enrollHistory.getBatch_code() + "_" + enrollHistory.getCategory())){
                    Lnyxmc lnyxmc = new Lnyxmc();
                    lnyxmc.setYear(yearIndex);
                    lnyxmc.setAvgGrade(enrollHistory.getAvg_grade());
                    lnyxmc.setAvgRanking(enrollHistory.getAvg_ranking());
                    lnyxmc.setEnrollCunt(enrollHistory.getEnroll_count());
                    lnyxmc.setHighGrade(enrollHistory.getHigh_grade());
                    lnyxmc.setHighRanking(enrollHistory.getHigh_ranking());
                    lnyxmc.setLowGrade(enrollHistory.getLow_ranking());
                    lnyxmc.setLowRanking(enrollHistory.getLow_ranking());
                    lnyxmcMap.put(yearIndex + "_" + enrollHistory.getCollege_code() + "_" +
                            enrollHistory.getBatch_code() + "_" + enrollHistory.getCategory(), lnyxmc);

                }

                //记录院校所录取的专业列表
                if (!schoolMajorMap.keySet().contains(enrollHistory.getCollege_code() + "_" +
                        enrollHistory.getBatch_code() + "_" + enrollHistory.getCategory())){
                    schoolMajorMap.put(enrollHistory.getCollege_code() + "_" +
                            enrollHistory.getBatch_code() + "_" + enrollHistory.getCategory(), new ArrayList<String>());
                }

                schoolMajorMap.get(enrollHistory.getCollege_code() + "_" + enrollHistory.getBatch_code() +
                        "_" + enrollHistory.getCategory()).add(enrollHistory.getMajor_name());

                //获取到已设置的院校录取情况，添加专业录取情况
                if (!allMajorMap.keySet().contains(yearIndex + "_" + enrollHistory.getCollege_code() + "_" +
                        enrollHistory.getBatch_code() + "_" + enrollHistory.getCategory() +
                        enrollHistory.getMajor_name())){
                    Lnzymc lnzymc = new Lnzymc();
                    lnzymc.setLowGrade(enrollHistory.getZy_low_grade());
                    lnzymc.setAvgGrade(enrollHistory.getZy_avg_grade());
                    lnzymc.setHighGrade(enrollHistory.getZy_high_grade());
                    lnzymc.setHighRanking(enrollHistory.getZy_high_ranking());
                    lnzymc.setLowRanking(enrollHistory.getZy_low_ranking());
                    lnzymc.setAvgRanking(enrollHistory.getZy_avg_ranking());
                    lnzymc.setMajorName(enrollHistory.getMajor_name());
                    lnzymc.setEnrollCunt(enrollHistory.getZy_enroll_count());
                    lnzymc.setYear(yearIndex);
                    allMajorMap.put(yearIndex + "_" + enrollHistory.getCollege_code() + "_" +
                            enrollHistory.getBatch_code() + "_" + enrollHistory.getCategory() + "_" +
                            enrollHistory.getMajor_name(), lnzymc);
                }


            }

            LOGGER.info("年份{}的原始数据查询并初始化完成",yearIndex);
        }

        //4.处理原始数据
        //第一步，处理完整院校原始数据，目标：保证每个曾经出现过的院校+科类+批次的组合在每年都有记录
        for (String resultIndex : probabilityCalaDTOMap.keySet()){
            LOGGER.info("处理院校{}：的原始数据--start",probabilityCalaDTOMap.get(resultIndex).getCollegeName());
            probabilityCalaDTOMap.get(resultIndex).setYxRankingMap(new HashMap<Integer, Lnyxmc>());

            for (Integer yearIndex = currentYear - 1; yearIndex >= currentYear - calcYears ;yearIndex -- ){
                //如果记录存在
                if (lnyxmcMap.keySet().contains(yearIndex + "_" + resultIndex)){
                    probabilityCalaDTOMap.get(resultIndex).getYxRankingMap().put(yearIndex,
                            lnyxmcMap.get(yearIndex + "_" + resultIndex));
                } else {

                    ProbabilityCalcRequest queryTarget = new ProbabilityCalcRequest();
                    BeanUtils.copyProperties(probabilityCalcRequest,queryTarget);
                    //查固定学校
                    queryTarget.setTargetSchool(probabilityCalaDTOMap.get(resultIndex).getCollegeCode());
                    queryTarget.setYear(yearIndex);
                    queryTarget.setRanking(1);
                    List<CollegeEnrollHistory> enrollHistories = lnyxlqtjDao.queryCollegeEnrollHistory(queryTarget);
                    if (CollectionUtils.isEmpty(enrollHistories)){
                        //某院校不存在招生计划
                        Lnyxmc lnyxmc = new Lnyxmc();
                        lnyxmc.setYear(yearIndex);
                        lnyxmc.setAvgGrade(0f);
                        lnyxmc.setAvgRanking(0f);
                        lnyxmc.setEnrollCunt(0);
                        lnyxmc.setHighGrade(0f);
                        lnyxmc.setHighRanking(0f);
                        lnyxmc.setLowGrade(0f);
                        lnyxmc.setLowRanking(0f);
                        probabilityCalaDTOMap.get(resultIndex).getYxRankingMap().put(yearIndex,
                                lnyxmc);
                    } else {
                        //某院校存在招生计划,但是考不上
                        Lnyxmc lnyxmc = new Lnyxmc();
                        lnyxmc.setYear(yearIndex);
                        lnyxmc.setAvgGrade(-1f);
                        lnyxmc.setAvgRanking(-1f);
                        lnyxmc.setEnrollCunt(-1);
                        lnyxmc.setHighGrade(-1f);
                        lnyxmc.setHighRanking(-1f);
                        lnyxmc.setLowGrade(-1f);
                        lnyxmc.setLowRanking(-1f);
                        probabilityCalaDTOMap.get(resultIndex).getYxRankingMap().put(yearIndex,
                                lnyxmc);
                    }

                }

            }

            LOGGER.info("处理院校{}： 的原始数据完成--end",probabilityCalaDTOMap.get(resultIndex).getCollegeName());
        }

        //第二步，处理完整专业原始数据，目标：保证每个曾经出现过的院校+科类+批次的组合在每年都有记录
        for (String resultIndex : probabilityCalaDTOMap.keySet()){
            List<String> majorNameList = schoolMajorMap.get(resultIndex);
            //设置专业录取统计Map
            probabilityCalaDTOMap.get(resultIndex).setMajorEnrollDTOMap(new HashMap<String, MajorEnrollDTO>());
            LOGGER.info("处理院校{}所有专业： 查询出的原始数据---start   ",
                    probabilityCalaDTOMap.get(resultIndex).getCollegeName());
            for (String majorName : majorNameList){

                //对每个专业录取统计Map在不同的专业时候增加一个DTO记录
                probabilityCalaDTOMap.get(resultIndex).getMajorEnrollDTOMap().put(majorName,new MajorEnrollDTO());

                //设置DTO的内容
                probabilityCalaDTOMap.get(resultIndex).getMajorEnrollDTOMap().
                        get(majorName).setLnzymcMap(new HashMap<Integer, Lnzymc>());

                for (Integer yearIndex = currentYear - 1; yearIndex >= currentYear - calcYears ;yearIndex -- ) {
                    //如果记录存在
                    if (allMajorMap.keySet().contains(yearIndex + "_" + resultIndex + "_" + majorName)) {
                        probabilityCalaDTOMap.get(resultIndex).getMajorEnrollDTOMap().get(majorName)
                                .getLnzymcMap().put(yearIndex,
                                allMajorMap.get(yearIndex + "_" + resultIndex + "_" + majorName));
                    } else {

                        ProbabilityCalcRequest queryTarget = new ProbabilityCalcRequest();
                        BeanUtils.copyProperties(probabilityCalcRequest, queryTarget);
                        //查固定学校的固定专业
                        queryTarget.setTargetSchool(probabilityCalaDTOMap.get(resultIndex).getCollegeCode());
                        queryTarget.setYear(yearIndex);
                        queryTarget.setRanking(1);
                        queryTarget.setTargetMajor(majorName);
                        List<CollegeEnrollHistory> enrollHistories = lnyxlqtjDao.queryCollegeEnrollHistory(queryTarget);
                        if (CollectionUtils.isEmpty(enrollHistories)) {
                            //某专业不存在招生计划
                            Lnzymc lnzymc = new Lnzymc();
                            lnzymc.setLowGrade(0f);
                            lnzymc.setAvgGrade(0f);
                            lnzymc.setHighGrade(0f);
                            lnzymc.setHighRanking(0f);
                            lnzymc.setLowRanking(0f);
                            lnzymc.setAvgRanking(0f);
                            lnzymc.setMajorName(majorName);
                            lnzymc.setEnrollCunt(0);
                            lnzymc.setYear(yearIndex);
                            probabilityCalaDTOMap.get(resultIndex).getMajorEnrollDTOMap().get(majorName)
                                    .getLnzymcMap().put(yearIndex,lnzymc);
                        } else {
                            //某专业存在招生计划,但是考不上
                            Lnzymc lnzymc = new Lnzymc();
                            lnzymc.setLowGrade(-1f);
                            lnzymc.setAvgGrade(-1f);
                            lnzymc.setHighGrade(-1f);
                            lnzymc.setHighRanking(-1f);
                            lnzymc.setLowRanking(-1f);
                            lnzymc.setAvgRanking(-1f);
                            lnzymc.setMajorName(majorName);
                            lnzymc.setEnrollCunt(-1);
                            lnzymc.setYear(yearIndex);
                            probabilityCalaDTOMap.get(resultIndex).getMajorEnrollDTOMap().get(majorName)
                                    .getLnzymcMap().put(yearIndex,lnzymc);
                        }

                    }
                }
            }
            LOGGER.info("院校{}所有专业： 查询出的原始数据完成--end",
                    probabilityCalaDTOMap.get(resultIndex).getCollegeName());

        }

        LOGGER.info("组装完成，开始计算概率");
        //5.开始计算概率
        //5.1先计算院校招生概率
        for (ProbabilityCalaDTO calaDTO : probabilityCalaDTOMap.values()){
            //5.1.1先只计算存在招生计划的
            for (Lnyxmc lnyxmc : calaDTO.getYxRankingMap().values()) {
                if (lnyxmc.getEnrollCunt() > 0){
                    if(probabilityCalcRequest.getRanking() < lnyxmc.getHighRanking()){
                        lnyxmc.setGaiLv(1d);
                    } else if (probabilityCalcRequest.getRanking() > lnyxmc.getLowRanking()){
                        lnyxmc.setGaiLv(0d);
                    } else {
                        lnyxmc.setGaiLv((lnyxmc.getLowRanking() - probabilityCalcRequest.getRanking())/
                                (lnyxmc.getLowRanking() - lnyxmc.getHighRanking()));
                    }
                }
                if (lnyxmc.getEnrollCunt() == -1){
                    lnyxmc.setGaiLv(0d);
                }
            }
        }
        LOGGER.info("院校概率：完成计算存在招生计划的和未被录取的");
        //5.1.2先院校找出一个基准概率，如果当年无录取计划的，设置为该基准概率
        Map<String,Double> defaultGailv = new HashMap<>();
        for (ProbabilityCalaDTO calaDTO : probabilityCalaDTOMap.values()){
            //计算一个默认概率
            for (Lnyxmc lnyxmc : calaDTO.getYxRankingMap().values()) {
                if (lnyxmc.getGaiLv() > 0.0d ){
                    defaultGailv.put(calaDTO.getCollegeCode() + "_" + calaDTO.getBatchCode() + "_" + calaDTO.getCategory(),
                            lnyxmc.getGaiLv());
                    break;
                }
            }
        }
        LOGGER.info("院校概率：查询并构造基础概率");
        for (ProbabilityCalaDTO calaDTO : probabilityCalaDTOMap.values()){
            //5.1.3处理无招生计划的
            for (Lnyxmc lnyxmc : calaDTO.getYxRankingMap().values()) {
                if (lnyxmc.getEnrollCunt() == 0){
                    if (defaultGailv.containsKey(calaDTO.getCollegeCode() + "_" +
                            calaDTO.getBatchCode() + "_" + calaDTO.getCategory())) {
                        lnyxmc.setGaiLv(defaultGailv.get(calaDTO.getCollegeCode() + "_" +
                                calaDTO.getBatchCode() + "_" + calaDTO.getCategory()));
                    } else {
                        lnyxmc.setGaiLv(0.0d);
                    }
                }
            }
        }
        LOGGER.info("院校概率：完成无招生计划院校的概率设置");
        //5.1.4对概率按年份加权,可能查过去2年或者过去3年
        for (ProbabilityCalaDTO calaDTO : probabilityCalaDTOMap.values()){
            List<Double> gailv = new ArrayList<>();
            for (Integer yearIndex = currentYear - 1; yearIndex >= currentYear - calcYears ;yearIndex -- ){
                gailv.add(calaDTO.getYxRankingMap().get(yearIndex).getGaiLv());
            }
            if (gailv.size() == 2){
                calaDTO.setGaiLv(gailv.get(0) * 0.6 + gailv.get(1)* 0.4);
            }
            if (gailv.size() == 3){
                calaDTO.setGaiLv(gailv.get(0) * 0.5 + gailv.get(1)* 0.3 + gailv.get(2)* 0.2);
            }
        }

        LOGGER.info("院校概率：完成加权概率计算");

        //5.2先计算专业招生概率
        for (ProbabilityCalaDTO calaDTO : probabilityCalaDTOMap.values()){
            //5.2.1先只计算存在招生计划的
            for (MajorEnrollDTO enrollDTO : calaDTO.getMajorEnrollDTOMap().values()) {

                for (Lnzymc lnzymc : enrollDTO.getLnzymcMap().values()){
                    if (lnzymc.getEnrollCunt() > 0){
                        if(probabilityCalcRequest.getRanking() < lnzymc.getHighRanking()){
                            lnzymc.setGaiLv(1d);
                        } else if (probabilityCalcRequest.getRanking() > lnzymc.getLowRanking()){
                            lnzymc.setGaiLv(0d);
                        } else {
                            lnzymc.setGaiLv((lnzymc.getLowRanking() - probabilityCalcRequest.getRanking())/
                                    (lnzymc.getLowRanking() - lnzymc.getHighRanking()));
                        }
                    }
                    //某年考不上
                    if (lnzymc.getEnrollCunt() == -1){
                        lnzymc.setGaiLv(0d);
                    }
                }

            }
        }
        LOGGER.info("专业概率：完成计算存在招生计划的和未被录取的");

        //5.2.2先给专业找出一个基准概率，如果某年无录取计划的，设置为该基准概率
        Map<String,Double> defaultZyGailv = new HashMap<>();
        for (ProbabilityCalaDTO calaDTO : probabilityCalaDTOMap.values()){
            //5.2.2先只计算存在招生计划的
            for (Map.Entry<String, MajorEnrollDTO> enrollDTO: calaDTO.getMajorEnrollDTOMap().entrySet()) {
                for (Lnzymc lnzymc : enrollDTO.getValue().getLnzymcMap().values()){
                    boolean flag = false;
                    if (lnzymc.getGaiLv() > 0.0d){
                        defaultZyGailv.put(calaDTO.getCollegeCode() + "_" + calaDTO.getBatchCode() + "_"
                                + calaDTO.getCategory() + "_" + enrollDTO.getKey(), lnzymc.getGaiLv());
                        flag = true;
                    }
                    if (flag){
                        break;
                    }
                }

            }
        }
        LOGGER.info("专业概率：完成计算并保存基准概率");

        for (ProbabilityCalaDTO calaDTO : probabilityCalaDTOMap.values()){
            //5.2.3处理无招生计划的
            for (Map.Entry<String, MajorEnrollDTO> enrollDTO: calaDTO.getMajorEnrollDTOMap().entrySet()) {
                for (Lnzymc lnzymc : enrollDTO.getValue().getLnzymcMap().values()) {
                    if (lnzymc.getEnrollCunt() == 0) {
                        if (!defaultZyGailv.containsKey(calaDTO.getCollegeCode() + "_" + calaDTO.getBatchCode() + "_"
                                + calaDTO.getCategory() + "_" + enrollDTO.getKey())){
                            lnzymc.setGaiLv(0.0d);
                        } else {
                            lnzymc.setGaiLv(defaultZyGailv.get(calaDTO.getCollegeCode() + "_" + calaDTO.getBatchCode() + "_"
                                    + calaDTO.getCategory() + "_" + enrollDTO.getKey()));
                        }
                    }
                }
            }
        }
        LOGGER.info("专业概率：设置无招生计划年份的概率");
        //5.2.4计算专业加权概率
        for (ProbabilityCalaDTO calaDTO : probabilityCalaDTOMap.values()){

            for (Map.Entry<String, MajorEnrollDTO> enrollDTO: calaDTO.getMajorEnrollDTOMap().entrySet()) {
                List<Double> gailv = new ArrayList<>();
                for (Lnzymc lnzymc : enrollDTO.getValue().getLnzymcMap().values()) {
                    gailv.add(lnzymc.getGaiLv());
                }
                if (gailv.size() == 2){
                    enrollDTO.getValue().setGaiLv(gailv.get(0) * 0.6 + gailv.get(1)* 0.4);
                }
                if (gailv.size() == 3){
                    enrollDTO.getValue().setGaiLv(gailv.get(0) * 0.5 + gailv.get(1)* 0.3 + gailv.get(2)* 0.2);
                }
            }

        }

        LOGGER.info("专业概率：完成所有专业加权概率设置");

        //6.获取非第一志愿录取率



        //7.开始排序,先做按照学校排名排序
        //排序可能有三种，按院校排名，按总录取概率，按非第一志愿录取率
        TreeSet<ProbabilityCalaDTO> sortedSet = new TreeSet<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                ProbabilityCalaDTO h1 = (ProbabilityCalaDTO) o1;
                ProbabilityCalaDTO h2 = (ProbabilityCalaDTO) o2;
                String h1Ranking = h1.getCollegeRanking();
                if (null == h1Ranking || h1Ranking.equals("")){
                    h1Ranking = "9999";
                }
                String h2Ranking = h2.getCollegeRanking();
                if (null == h2Ranking || h2Ranking.equals("")){
                    h2Ranking = "9999";
                }
                try {
                    if (Integer.valueOf(h1Ranking) < Integer.valueOf(h2Ranking)) {
                        return -1;
                    }
                    if (Integer.valueOf(h1Ranking) > Integer.valueOf(h2Ranking)) {
                        return 1;
                    }
                } catch (Exception e){
                    LOGGER.error("院校名次比较出错");
                    return 0;
                }
                return 0;
            }
        });

        for (ProbabilityCalaDTO calaDTO : probabilityCalaDTOMap.values()){
            sortedSet.add(calaDTO);
        }

        response.setResult(true);
        response.setProbabilityCalaDTOs(new ArrayList<ProbabilityCalaDTO>(sortedSet));
        return response;
    }


}
