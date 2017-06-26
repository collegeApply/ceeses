package com.ceeses.extractor;

import com.ceeses.dao.LnyxlqtjDao;
import com.ceeses.http.util.HttpClientUtil;
import com.ceeses.model.Lnyxlqtj;
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
 * 历年院校录取统计数据提取器
 *
 * @author deng.zhang
 * @since 1.0 created on 2017/6/7
 */
@Component
public class LnyxlqtjExtractor {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LnyxlqtjExtractor.class);
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
    private LnyxlqtjDao lnyxlqtjDao;

    public Integer extract() {
        LOGGER.info("提取历年院校录取统计开始......");
        long startTime = System.currentTimeMillis();

        List<Future<Integer>> futures = new ArrayList<>(years.size());
        for (Integer year : years) {
            futures.add(executorService.submit(new ExtractTask(year)));
        }

        int count = 0;
        for (Future<Integer> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                LOGGER.error("提取数据发生异常", e);
            }
        }

        LOGGER.info("提取历年院校录取统计结束, 总记录数: {}, 耗时: {}ms", count, System.currentTimeMillis() - startTime);
        return count;
    }

    /**
     * 分页提取历年院校录取统计信息
     *
     * @param params 参数
     * @return 历年院校录取统计信息列表
     */
    private List<Lnyxlqtj> extractLnyxlqtjByPage(Map<String, String> params) {
        List<Lnyxlqtj> lnyxlqtjs = new ArrayList<>();
        String html = HttpClientUtil.post(lnyxlqtjPageUrl, params).getHtml();
        if (html != null && !"".equals(html.trim())) {
            Document document = Jsoup.parse(html);
            Element dataTable = document.getElementById("GridView1");
            Elements dataTableTrs = dataTable.select("tr");
            // 第一个TR是表头
            if (dataTableTrs.size() > 1) {
                for (Element dataTableTr : dataTableTrs) {
                    Elements tds = dataTableTr.select("td");
                    if (tds.size() == 9) {
                        Lnyxlqtj lnyxlqtj = new Lnyxlqtj();
                        lnyxlqtj.setYear(Integer.valueOf(params.get("nf")));
                        lnyxlqtj.setCollegeName(tds.get(0).text());
                        lnyxlqtj.setBatchCode(tds.get(1).text());
                        lnyxlqtj.setCategory(tds.get(2).text());
                        lnyxlqtj.setEnrollCount(Integer.valueOf(tds.get(3).text()));
                        String highGrade = tds.get(4).text();
                        lnyxlqtj.setHighGrade(Float.valueOf(StringUtils.hasText(highGrade) ? highGrade : "0.0"));
                        String highRanking = tds.get(5).text();
                        lnyxlqtj.setHighRanking(Integer.valueOf(StringUtils.hasText(highRanking) ? highRanking : "0"));
                        String lowGrade = tds.get(6).text();
                        lnyxlqtj.setLowGrade(Float.valueOf(StringUtils.hasText(lowGrade) ? lowGrade : "0.0"));
                        String lowRanking = tds.get(7).text();
                        lnyxlqtj.setLowRanking(Integer.valueOf(StringUtils.hasText(lowRanking) ? lowRanking : "0"));
                        lnyxlqtj.setAvgGrade(0.0f);
                        lnyxlqtj.setAvgRanking(0.0f);

                        lnyxlqtjs.add(lnyxlqtj);
                    }
                }
            }
        }

        return lnyxlqtjs;
    }

    private class ExtractTask implements Callable<Integer> {
        /**
         * 年份
         */
        private Integer year;

        public ExtractTask(Integer year) {
            this.year = year;
        }

        @Override
        public Integer call() throws Exception {
            Map<String, String> params = new HashMap<>();
            params.put("nf", year.toString());
            Document document = Jsoup.parse(HttpClientUtil.post(lnyxlqtjPageUrl, params).getHtml());

            // 批次代码
            List<String> batchCodes = new ArrayList<>();
            for (Element element : document.getElementById("pcdm").select("option")) {
                String value = element.attr("value");
                if (!"".equals(value)) {
                    batchCodes.add(value);
                }
            }
            LOGGER.info("提取批次代码结束, {}", batchCodes);

            // 科别
            List<String> kldmList = new ArrayList<>();
            for (Element element : document.getElementById("kldm").select("option")) {
                String value = element.attr("value");
                if (!"".equals(value)) {
                    kldmList.add(value);
                }
            }
            LOGGER.info("提取科别信息结束, {}", kldmList);

            Integer count = 0;
            for (String batchCode : batchCodes) {
                for (String kldm : kldmList) {
                    params.put("nf", year.toString());
                    params.put("pcdm", batchCode);
                    params.put("kldm", kldm);
                    params.remove("pageNum");
                    PageInfo pageInfo = PageInfoUtil.extractPageInfo(lnyxlqtjPageUrl, params);
                    LOGGER.info("提取参数为{}的数据, {}", params, pageInfo);
                    long startTime = System.currentTimeMillis();
                    for (int i = 1; i <= pageInfo.getTotalPage(); i++) {
                        params.put("pageNum", String.valueOf(i));
                        List<Lnyxlqtj> lnyxlqtjs = extractLnyxlqtjByPage(params);
                        if (lnyxlqtjs.size() > 0) {
                            lnyxlqtjDao.batchSaveLnyxlqtj(lnyxlqtjs);
                        }
                        count += lnyxlqtjs.size();
                    }
                    LOGGER.info("提取参数为{}的数据结束, {}, 耗时: {}ms", params, pageInfo, System.currentTimeMillis() - startTime);
                }
            }

            return count;
        }
    }
}
