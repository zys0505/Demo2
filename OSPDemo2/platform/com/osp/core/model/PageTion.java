package com.osp.core.model;

import java.util.List;

import com.github.pagehelper.Page;

/**
 * 分页实体封装
 * @author liudonghe  2017年5月10日 下午3:43:31 
 * @param <T>
 */
public class PageTion<T> {
    //开始行
    private int beginRows;
    //页面大小
    private int size;
    //结束行
    private int endRows;
    //页号
    private int index;
    //数据总量
    private int total;
    //最大页数
    private int pageMax;
    
    private List<T> data;

    public PageTion() {

    }

    public PageTion(List<T> list) {
        this.data = list;
        if (list instanceof Page) {
            Page<T> p = (Page<T>) list;
            this.total = (int) p.getTotal();
            this.endRows = p.getEndRow();
            this.index = p.getPageNum();
            this.size = p.getPageSize();
            this.pageMax = total % size == 0 ? total / size : total / size + 1;
        }
    }

    public int getBeginRows() {
        return beginRows;
    }

    public void setBeginRows(int beginRows) {
        this.beginRows = beginRows;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getEndRows() {
        return endRows;
    }

    public void setEndRows(int endRows) {
        this.endRows = endRows;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public int getPageMax() {
        return pageMax;
    }

    public void setPageMax(int pageMax) {
        this.pageMax = pageMax;
    }
}
