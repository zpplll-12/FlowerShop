package com.flower.util;
import java.util.List;

public class PageBean<T> {
    private int pageNum;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<T> list;

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.totalPage = (totalCount + pageSize - 1) / pageSize;
    }

    public int getPageNum() {return pageNum;}
    public void setPageNum(int pageNum) {this.pageNum = pageNum;}
    public int getPageSize() {return pageSize;}
    public void setPageSize(int pageSize) {this.pageSize = pageSize;}
    public int getTotalCount() {return totalCount;}
    public int getTotalPage() {return totalPage;}
    public List<T> getList() {return list;}
    public void setList(List<T> list) {this.list = list;}
}