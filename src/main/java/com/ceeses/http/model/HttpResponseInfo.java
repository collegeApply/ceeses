package com.ceeses.http.model;

/**
 * HTTP请求响应信息
 *
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-05-30 17:34
 */
public class HttpResponseInfo {
    /**
     * 响应状态码
     * -1表示请求失败
     */
    private Integer statusCode = -1;
    /**
     * HTML内容
     */
    private String html;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
