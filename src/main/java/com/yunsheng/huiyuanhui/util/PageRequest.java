package com.yunsheng.huiyuanhui.util;

import org.apache.ibatis.session.RowBounds;
import org.springframework.core.annotation.Order;

import java.io.Serializable;
import java.util.List;

/**
 * 分页插件
 */
public class PageRequest<T> extends RowBounds implements Serializable {
    private static final long serialVersionUID = 1913214886364153519L;
    private int curPage = 1;
    private int pageData = 10;
    private int totalData;
    private int totalPage;
    private int offset;
    private int limit;
    private Order[] orders;
    private List<T> dataList;
    private boolean isCounted = true;

    public PageRequest() {
    }

    public PageRequest(int curPage, int pageData) {
        this.curPage = curPage;
        this.pageData = pageData;
    }

    public PageRequest(int curPage, int pageData, boolean isCounted) {
        this.curPage = curPage;
        this.pageData = pageData;
        this.isCounted = isCounted;
    }

    public PageRequest(int curPage, int pageData, Order... orders) {
        this.curPage = curPage;
        this.pageData = pageData;
        this.orders = orders;
    }

    public PageRequest(int curPage, int pageData, boolean isCounted, Order... orders) {
        this.curPage = curPage;
        this.pageData = pageData;
        this.orders = orders;
        this.isCounted = isCounted;
    }

    public Order[] getOrders() {
        return this.orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public PageRequest<T> data(List<T> dataList) {
        this.dataList = dataList;
        return this;
    }

    public int getCurPage() {
        return this.curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotalData() {
        return this.totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
        if(0 != totalData) {
            this.totalPage = totalData % this.pageData == 0?totalData / this.pageData:totalData / this.pageData + 1;
        }

    }

    public int getOffset() {
        return this.offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getPageData() {
        return this.pageData;
    }

    public void setPageData(int pageData) {
        this.pageData = pageData;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public boolean isCounted() {
        return this.isCounted;
    }

    public void setCounted(boolean isCounted) {
        this.isCounted = isCounted;
    }

    public void setTotalDataAndDBType(int totalData, String dbType) {
        this.setTotalData(totalData);
        this.setOffset(dbType);
        this.setLimit(dbType);
    }

    private void setOffset(String dbType) {
        if("MySQL".equalsIgnoreCase(dbType)) {
            this.offset = (this.curPage - 1) * this.pageData;
        } else {
            this.offset = this.curPage > 0?(this.curPage - 1) * this.pageData + 1:1;
        }

    }

    private void setLimit(String dbType) {
        if("MySQL".equalsIgnoreCase(dbType)) {
            this.limit = this.pageData;
        } else {
            this.limit = this.curPage > 0?this.curPage * this.pageData:this.pageData;
        }

    }
}
