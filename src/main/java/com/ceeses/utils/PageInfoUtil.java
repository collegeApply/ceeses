package com.ceeses.utils;

import com.ceeses.http.util.HttpClientUtil;
import com.ceeses.model.PageInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/8
 */
public class PageInfoUtil {
    /**
     * 提取分页信息
     *
     * @param url    页面
     * @param params 参数
     * @return 分页信息
     */
    public static PageInfo extractPageInfo(String url, Map<String, String> params) {
        PageInfo pageInfo = new PageInfo();
        String html = HttpClientUtil.post(url, params).getHtml();
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
}
