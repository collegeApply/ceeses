package com.ceeses.extractor;

import com.ceeses.dao.LnzylqtjDao;
import com.ceeses.http.util.HttpClientUtil;
import com.ceeses.model.Lnzylqtj;
import com.ceeses.model.PageInfo;
import com.ceeses.utils.PageInfoUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 历年专业录取统计数据提取器
 *
 * @author deng.zhang
 * @since 1.0 created on 2017/6/8
 */
@Component
public class LnzylqtjExtractor {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LnzylqtjExtractor.class);
    /**
     * 历年院校录取统计
     */
    private String lnyxlqtjPageUrl = "http://www.qhzk.com/YXLQShow.action";
    /**
     * 爬取数据的年份
     */
    @Value("#{'${extract.years}'.split(',')}")
    private List<Integer> years;
    /**
     * 线程池
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    @Autowired
    private LnzylqtjDao lnzylqtjDao;

    public void extract() {
        LOGGER.info("提取历年院校信息");
        long startTime = System.currentTimeMillis();

        List<Future<Map<Integer, Map<String, String>>>> collegeInfoFutures = new ArrayList<>(years.size());
        for (Integer year : years) {
            collegeInfoFutures.add(executorService.submit(new ExtractCollegeInfoTask(year)));
        }

        Map<Integer, Map<String, String>> collegeInfoMap = new HashMap<>();
        for (Future<Map<Integer, Map<String, String>>> future : collegeInfoFutures) {
            try {
                Map<Integer, Map<String, String>> map = future.get();
                if (map != null) {
                    for (Map.Entry<Integer, Map<String, String>> entry : map.entrySet()) {
                        if (collegeInfoMap.get(entry.getKey()) == null) {
                            collegeInfoMap.put(entry.getKey(), entry.getValue());
                        } else {
                            collegeInfoMap.get(entry.getKey()).putAll(entry.getValue());
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("提取院校信息发生异常", e);
            }
        }
        LOGGER.info("提取历年院校信息结束");

        LOGGER.info("提取历年专业录取统计开始......");
        int total = 0;
        if (collegeInfoMap.size() > 0) {
            List<Future<Integer>> futures = new ArrayList<>(years.size());
            for (Map.Entry<Integer, Map<String, String>> entry : collegeInfoMap.entrySet()) {
                if (entry.getValue().size() > 0) {
                    futures.add(executorService.submit(new ExtractTask(entry.getKey(), entry.getValue())));
                }
            }
            for (Future<Integer> future : futures) {
                try {
                    Integer count = future.get();
                    if (count != null) {
                        total += count;
                    }
                } catch (Exception e) {
                    LOGGER.error("提取数据发生异常", e);
                }
            }
        }
        LOGGER.info("提取历年专业录取统计结束, 总记录数: {}, 耗时: {}ms", total, System.currentTimeMillis() - startTime);
    }

    private class ExtractCollegeInfoTask implements Callable<Map<Integer, Map<String, String>>> {
        /**
         * 年份
         */
        private Integer year;

        ExtractCollegeInfoTask(Integer year) {
            this.year = year;
        }

        @Override
        public Map<Integer, Map<String, String>> call() throws Exception {
            Map<Integer, Map<String, String>> map = new HashMap<>();
            map.put(year, new HashMap<String, String>());
            Map<String, String> params = new HashMap<>();
            params.put("nf", year.toString());
            PageInfo pageInfo = PageInfoUtil.extractPageInfo(lnyxlqtjPageUrl, params);
            LOGGER.info("提取{}年的院校信息, {}", year, pageInfo);
            long startTime = System.currentTimeMillis();
            for (int i = 1; i <= pageInfo.getTotalPage(); i++) {
                params.put("pageNum", String.valueOf(i));
                map.get(year).putAll(extractCollegeInfoByPage(params));
                LOGGER.info("提取{}年的数据, 已提取{}条数据", year, map.get(year).size());
            }
            LOGGER.info("提取{}年的院校信息结束, 共{}条数据, 耗时: {}ms", year, map.get(year).size(), System.currentTimeMillis() - startTime);

            return map;
        }

        /**
         * 分页提取院校信息
         * 包括院校名称和获取该院校历年专业详情录取统计的链接
         *
         * @param params 参数
         * @return 院校信息列表
         */
        private Map<String, String> extractCollegeInfoByPage(Map<String, String> params) {
            // key是院校名称，value是该院校历年专业详情录取统计的链接
            Map<String, String> map = new HashMap<>();
            String html = HttpClientUtil.post(lnyxlqtjPageUrl, params).getHtml();
            if (html != null && !"".equals(html.trim())) {
                Document document = Jsoup.parse(html);
                Element dataTable = document.getElementById("GridView1");
                Elements dataTableTrs = dataTable.select("tr");
                // 第一个TR是表头
                if (dataTableTrs.size() > 1) {
                    for (Element dataTableTr : dataTableTrs) {
                        Elements tds = dataTableTr.select("td");
                        if (tds.size() == 8) {
                            Element collegeNameElement = tds.get(0).getElementsByTag("a").get(0);
                            map.put(collegeNameElement.text(), "http://www.qhzk.com/" + collegeNameElement.attr("href"));
                        }
                    }
                }
            }

            return map;
        }
    }

    private class ExtractTask implements Callable<Integer> {
        /**
         * 年份
         */
        private Integer year;
        private Map<String, String> collegeInfoMap;

        ExtractTask(Integer year, Map<String, String> collegeInfoMap) {
            this.year = year;
            this.collegeInfoMap = collegeInfoMap;
        }

        @Override
        public Integer call() throws Exception {
            LOGGER.info("提取{}年的历年专业录取统计数据", year);
            long startTime = System.currentTimeMillis();
            int count = 0;
            for (Map.Entry<String, String> entry : collegeInfoMap.entrySet()) {
                String html = HttpClientUtil.get(entry.getValue()).getHtml();
                if (html != null && !"".equals(html.trim())) {
                    List<Lnzylqtj> lnzylqtjs = new ArrayList<>();
                    Document document = Jsoup.parse(html);
                    Element dataTable = document.getElementById("GridView1");
                    Elements dataTableTrs = dataTable.select("tr");
                    // 第一个TR是表头
                    if (dataTableTrs.size() > 1) {
                        for (Element dataTableTr : dataTableTrs) {
                            Elements tds = dataTableTr.select("td");
                            if (tds.size() == 8) {
                                Lnzylqtj lnzylqtj = new Lnzylqtj();
                                lnzylqtj.setYear(year);
                                lnzylqtj.setCollegeName(entry.getKey());
                                lnzylqtj.setBatchCode(tds.get(0).text());
                                lnzylqtj.setMajorName(tds.get(1).text());
                                lnzylqtj.setCategory(tds.get(2).text());
                                lnzylqtj.setEnrollCount(Integer.valueOf(tds.get(3).text()));
                                String highGrade = tds.get(4).text();
                                lnzylqtj.setHighGrade(Float.valueOf(StringUtils.hasText(highGrade) ? highGrade : "0.0"));
                                String highRanking = tds.get(5).text();
                                lnzylqtj.setHighRanking(Integer.valueOf(StringUtils.hasText(highRanking) ? highRanking : "0"));
                                String lowGrade = tds.get(6).text();
                                lnzylqtj.setLowGrade(Float.valueOf(StringUtils.hasText(lowGrade) ? lowGrade : "0.0"));
                                String lowRanking = tds.get(7).text();
                                lnzylqtj.setLowRanking(Integer.valueOf(StringUtils.hasText(lowRanking) ? lowRanking : "0"));
                                lnzylqtj.setAvgGrade(0.0f);
                                lnzylqtj.setAvgRanking(0.0f);

                                lnzylqtjs.add(lnzylqtj);
                                count++;
                            }
                        }
                    }
                    if (lnzylqtjs.size() > 0) {
                        lnzylqtjDao.batchSaveLnzylqtj(lnzylqtjs);
                    }
                }
            }

            LOGGER.info("提取{}年的历年专业录取统计数据结束, 共{}条数据, 耗时: {}ms", year, count, System.currentTimeMillis() - startTime);

            return count;
        }
    }
}
