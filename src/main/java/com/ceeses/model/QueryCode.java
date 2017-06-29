package com.ceeses.model;

import java.util.Date;

/**
 * 查询码
 *
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2017-06-29 20:39
 */
public class QueryCode {
    /**
     * ID
     */
    private Integer id;
    /**
     * 查询码
     */
    private String code;
    /**
     * 状态，0表示可用，1表示不可用
     */
    private String status = Status.AVAILABLE;
    /**
     * 创建时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static class Status {
        /**
         * 可用
         */
        public static final String AVAILABLE = "0";
        /**
         * 不可用
         */
        public static final String UNAVAILABLE = "1";

        private Status() {
            throw new UnsupportedOperationException("不能实例化该类");
        }
    }
}
