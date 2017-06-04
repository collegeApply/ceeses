package com.ceeses.http.util;

import com.ceeses.http.model.HttpResponseInfo;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP客户端工具
 *
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-05-29 21:34
 */
public class HttpClientUtil {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    /**
     * 客户端
     */
    private static CloseableHttpClient client = HttpClients.createDefault();

    public static HttpResponseInfo get(String url) {
        HttpGet get = new HttpGet(url);

        HttpResponseInfo responseInfo = new HttpResponseInfo();
        HttpResponse response;
        try {
            response = client.execute(get);
            responseInfo.setStatusCode(response.getStatusLine().getStatusCode());
            responseInfo.setHtml(EntityUtils.toString(response.getEntity()));
            LOGGER.info("访问页面{}成功, {}", url, response.getStatusLine());
        } catch (IOException e) {
            LOGGER.error("访问页面{}发生异常", url, e);
        }

        return responseInfo;
    }

    public static HttpResponseInfo post(String url) throws IOException {
        return post(url, null);
    }

    public static HttpResponseInfo post(String url, Map<String, String> params) {
        HttpPost post = new HttpPost(url);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        HttpResponseInfo responseInfo = new HttpResponseInfo();
        try {
            if (nameValuePairs.size() > 0) {
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            HttpResponse response = client.execute(post);
            responseInfo.setStatusCode(response.getStatusLine().getStatusCode());
            responseInfo.setHtml(EntityUtils.toString(response.getEntity()));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("URL: {}, Params: {}", url, params, e);
        } catch (IOException e) {
            LOGGER.error("访问页面{}发生异常, 参数: {}", url, params, e);
        }

        return responseInfo;
    }

    public static void close() throws IOException {
        client.close();
    }
}
