package com.ceeses.model;

/**
 * @author deng.zhang
 * @since 1.0 created on 2017/6/8
 */
public class PageInfo {
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
