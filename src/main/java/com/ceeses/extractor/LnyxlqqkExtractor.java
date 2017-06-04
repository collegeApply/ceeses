package com.ceeses.extractor;

import com.ceeses.dao.LnyxlqqkDao;
import com.ceeses.http.util.HttpClientUtil;
import com.ceeses.model.Lnyxlqqk;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-05-29 21:33
 */
@Component
public class LnyxlqqkExtractor {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LnyxlqqkExtractor.class);
    /**
     * 历年院校录取情况
     */
    private String lnyxlqqkPageUrl = "http://www.qhzk.com/lnlqqkk.action";
    /**
     * 线程池
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    @Autowired
    private LnyxlqqkDao lnyxlqqkDao;

    public Integer extract() throws IOException {
        LOGGER.info("提取历年院校录取情况开始......");
        long startTime = System.currentTimeMillis();

        Document document = Jsoup.parse(HttpClientUtil.post(lnyxlqqkPageUrl).getHtml());

        // 年份
        List<Integer> years = new ArrayList<>();
        for (Element element : document.getElementById("ddl_nf").select("option")) {
            years.add(Integer.valueOf(element.attr("value")));
        }
        LOGGER.info("提取年份信息结束, {}", years);

        // 科别
        List<String> kldmList = new ArrayList<>();
        for (Element element : document.getElementById("ddl_kb").select("option")) {
            String value = element.attr("value");
            if (!"".equals(value)) {
                kldmList.add(value);
            }
        }
        LOGGER.info("提取科别信息结束, {}", kldmList);

        // 地区
        List<String> dydmList = new ArrayList<>();
        for (Element element : document.getElementById("dydm").select("option")) {
            String value = element.attr("value");
            if (!"".equals(value)) {
                dydmList.add(value);
            }
        }
        LOGGER.info("提取地区信息结束, {}", dydmList);

        List<Future<Integer>> futures = new ArrayList<>(years.size());
        for (Integer year : years) {
            futures.add(executorService.submit(new ExtractTask(year, kldmList, dydmList)));
        }

        int count = 0;
        for (Future<Integer> future : futures) {
            try {
                count += future.get();
            } catch (Exception e) {
                LOGGER.error("提取数据发生异常", e);
            }
        }

        LOGGER.info("提取历年院校录取情况结束, 总记录数: {}, 耗时: {}ms", count, System.currentTimeMillis() - startTime);
        return count;
    }

    /**
     * 提取分页信息
     *
     * @param params 参数
     * @return 分页信息
     */
    private PageInfo extractPageInfo(Map<String, String> params) {
        PageInfo pageInfo = new PageInfo();
        String html = HttpClientUtil.post(lnyxlqqkPageUrl, params).getHtml();
        if (html != null && !"".equals(html.trim())) {
            Document document = Jsoup.parse(html);
            // 获取分页信息
            Element pagerDiv = document.getElementById("AspNetPager1");
            String pageInfoString = pagerDiv.child(0).text();
            String[] pageInfoSegmentArray = pageInfoString.split("：");

            String pageInfoSegment1 = pageInfoSegmentArray[1].trim();
            String pageInfoSegment2 = pageInfoSegmentArray[2].trim();
            String pageInfoSegment3 = pageInfoSegmentArray[3].trim();

            pageInfo.setTotal(Integer.valueOf(pageInfoSegment1.substring(0, pageInfoSegment1.indexOf(" "))));
            pageInfo.setTotalPage(Integer.valueOf(pageInfoSegment3));
            pageInfo.setCurrentPageNo(Integer.valueOf(pageInfoSegment2.substring(0, 1)));
        }

        return pageInfo;
    }

    /**
     * 分页提取历年院校录取情况信息
     *
     * @param params 参数
     * @return 历年院校录取情况信息列表
     */
    private List<Lnyxlqqk> extractLnyxlqqkByPage(Map<String, String> params) {
        List<Lnyxlqqk> lnyxlqqks = new ArrayList<>();
        String html = HttpClientUtil.post(lnyxlqqkPageUrl, params).getHtml();
        if (html != null && !"".equals(html.trim())) {
            Document document = Jsoup.parse(html);
            Element dataTable = document.getElementById("GridView1");
            Elements dataTableTrs = dataTable.select("tr");
            // 第一个TR是表头
            if (dataTableTrs.size() > 1) {
                for (Element dataTableTr : dataTableTrs) {
                    Elements tds = dataTableTr.select("td");
                    if (tds.size() == 9) {
                        Lnyxlqqk lnyxlqqk = new Lnyxlqqk();
                        lnyxlqqk.setYear(Integer.valueOf(params.get("nf")));
                        lnyxlqqk.setCategoryName(params.get("kldm"));
                        lnyxlqqk.setAreaName(params.get("dydm"));
                        lnyxlqqk.setVolunteerNo(Integer.valueOf(tds.get(0).text()));
                        lnyxlqqk.setBatchCode(tds.get(1).text());
                        lnyxlqqk.setStudentGender(tds.get(3).text());
                        lnyxlqqk.setSchoolName(tds.get(4).text());
                        lnyxlqqk.setMajor(tds.get(5).text());
                        lnyxlqqk.setScore(Float.valueOf(tds.get(6).text()));
                        lnyxlqqk.setRanking(Integer.valueOf(tds.get(7).text()));
                        lnyxlqqk.setCastArchiveUnitName(tds.get(8).text());

                        lnyxlqqks.add(lnyxlqqk);
                    }
                }
            }
        }

        return lnyxlqqks;
    }

    private class PageInfo {
        /**
         * 总记录数
         */
        private Integer total;
        /**
         * 总页数
         */
        private Integer totalPage;
        /**
         * 当前页码
         */
        private Integer currentPageNo;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(Integer totalPage) {
            this.totalPage = totalPage;
        }

        public Integer getCurrentPageNo() {
            return currentPageNo;
        }

        public void setCurrentPageNo(Integer currentPageNo) {
            this.currentPageNo = currentPageNo;
        }

        @Override
        public String toString() {
            return "[总记录数: " + getTotal() + ", 总页数: " + getTotalPage() + ", 当前页: " + getCurrentPageNo() + "]";
        }
    }

    private class ExtractTask implements Callable<Integer> {
        /**
         * 年份
         */
        private Integer year;
        /**
         * 科别
         */
        private List<String> kldmList;
        /**
         * 地区
         */
        private List<String> dydmList;

        public ExtractTask(Integer year, List<String> kldmList, List<String> dydmList) {
            this.year = year;
            this.kldmList = kldmList;
            this.dydmList = dydmList;
        }

        @Override
        public Integer call() throws Exception {
            Map<String, String> params = new HashMap<>();
            Integer count = 0;
            for (String kldm : kldmList) {
                for (String dydm : dydmList) {
                    params.put("nf", year.toString());
                    params.put("kldm", kldm);
                    params.put("dydm", dydm);
                    params.remove("pageNum");
                    PageInfo pageInfo = extractPageInfo(params);
                    LOGGER.info("提取参数为{}的数据, {}", params, pageInfo);
                    long startTime = System.currentTimeMillis();
                    for (int i = 1; i <= pageInfo.getTotalPage(); i++) {
                        params.put("pageNum", String.valueOf(i));
                        List<Lnyxlqqk> lnyxlqqks = extractLnyxlqqkByPage(params);
                        lnyxlqqkDao.bulkSave(lnyxlqqks);
                        count += lnyxlqqks.size();
                    }
                    LOGGER.info("提取参数为{}的数据结束, {}, 耗时: {}ms", params, pageInfo, System.currentTimeMillis() - startTime);
                }
            }

            return count;
        }
    }
}
