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
     * 历年专业录取统计
     */
    private String lnzylqtjPageUrl = "http://www.qhzk.com/lnlqqk.action";
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
        LOGGER.info("提取历年专业录取统计开始......");
        long startTime = System.currentTimeMillis();

        List<Future<Integer>> futures = new ArrayList<>(years.size());
        for (Integer year : years) {
            futures.add(executorService.submit(new ExtractTask(year)));
        }

        int total = 0;
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

        LOGGER.info("提取历年专业录取统计结束, 总记录数: {}, 耗时: {}ms", total, System.currentTimeMillis() - startTime);
    }

    /**
     * 分页提取历年专业录取统计信息
     *
     * @param params 参数
     * @return 历年专业录取统计信息列表
     */
    private List<Lnzylqtj> extractLnzylqtjByPage(Map<String, String> params) {
        List<Lnzylqtj> lnzylqtjs = new ArrayList<>();
        String html = HttpClientUtil.post(lnzylqtjPageUrl, params).getHtml();
        if (html != null && !"".equals(html.trim())) {
            Document document = Jsoup.parse(html);
            Element dataTable = document.getElementById("GridView1");
            Elements dataTableTrs = dataTable.select("tr");
            // 第一个TR是表头
            if (dataTableTrs.size() > 1) {
                for (Element dataTableTr : dataTableTrs) {
                    Elements tds = dataTableTr.select("td");
                    if (tds.size() == 10) {
                        Lnzylqtj lnzylqtj = new Lnzylqtj();
                        lnzylqtj.setYear(Integer.valueOf(params.get("nf")));
                        lnzylqtj.setCollegeName(tds.get(0).text());
                        lnzylqtj.setMajorName(tds.get(1).text());
                        lnzylqtj.setBatchCode(tds.get(2).text());
                        lnzylqtj.setCategory(tds.get(3).text());
                        lnzylqtj.setEnrollCount(Integer.valueOf(tds.get(4).text()));
                        String highGrade = tds.get(5).text();
                        lnzylqtj.setHighGrade(Float.valueOf(StringUtils.hasText(highGrade) ? highGrade : "0.0"));
                        String highRanking = tds.get(6).text();
                        lnzylqtj.setHighRanking(Integer.valueOf(StringUtils.hasText(highRanking) ? highRanking : "0"));
                        String lowGrade = tds.get(7).text();
                        lnzylqtj.setLowGrade(Float.valueOf(StringUtils.hasText(lowGrade) ? lowGrade : "0.0"));
                        String lowRanking = tds.get(8).text();
                        lnzylqtj.setLowRanking(Integer.valueOf(StringUtils.hasText(lowRanking) ? lowRanking : "0"));
                        lnzylqtj.setAvgGrade(0.0f);
                        lnzylqtj.setAvgRanking(0.0f);

                        lnzylqtjs.add(lnzylqtj);
                    }
                }
            }
        }

        return lnzylqtjs;
    }

    private class ExtractTask implements Callable<Integer> {
        /**
         * 年份
         */
        private Integer year;

        ExtractTask(Integer year) {
            this.year = year;
        }

        @Override
        public Integer call() throws Exception {
            Map<String, String> params = new HashMap<>();
            params.put("nf", year.toString());
            Integer count = 0;
            PageInfo pageInfo = PageInfoUtil.extractPageInfo(lnzylqtjPageUrl, params);
            LOGGER.info("提取{}年的数据, {}", year, pageInfo);
            long startTime = System.currentTimeMillis();
            for (int i = 1; i <= pageInfo.getTotalPage(); i++) {
                params.put("pageNum", String.valueOf(i));
                List<Lnzylqtj> lnzylqtjs = extractLnzylqtjByPage(params);
                if (lnzylqtjs.size() > 0) {
                    lnzylqtjDao.batchSaveLnzylqtj(lnzylqtjs);
                }
                count += lnzylqtjs.size();
                LOGGER.info("提取{}年的数据, {}, 已提取{}条数据", year, params, count);
            }
            LOGGER.info("提取{}年的数据结束, {}, 共{}条数据, 耗时: {}ms", year, params, count, System.currentTimeMillis() - startTime);

            return count;
        }
    }
}
