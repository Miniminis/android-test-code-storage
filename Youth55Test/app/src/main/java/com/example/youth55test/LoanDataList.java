package com.example.youth55test;

import java.util.List;

public class LoanDataList {
    private int totPageCount;
    private List<LoanData> list;

//    public LoanDataList(int totPageCount) {
//        this.totPageCount = totPageCount;
//    }

    public LoanDataList(int totPageCount, List<LoanData> list) {
        this.totPageCount = totPageCount;
        this.list = list;
    }

    public int getTotPageCount() {
        return totPageCount;
    }

    public void setTotPageCount(int totPageCount) {
        this.totPageCount = totPageCount;
    }

    public List<LoanData> getList() {
        return list;
    }

    public void setList(List<LoanData> list) {
        this.list = list;
    }
}
