package com.example.cafeinadmin;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TotalOrderListData {
    private String orderNum,orderMnName, option, orderDate, orderAmnt, orderstatus, orderPrice;
    SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    Calendar time = Calendar.getInstance();

    public TotalOrderListData() {
    }

    public TotalOrderListData(String orderNum, String orderMnName,String option, String orderDate, String orderAmnt, String orderstatus, String orderPrice) {
        this.orderNum = orderNum;
        this.orderMnName = orderMnName;
        this.option =option;
        this.orderDate = orderDate;
        this.orderAmnt = orderAmnt;
        this.orderstatus = orderstatus;
        this.orderPrice = orderPrice;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderMnName() {
        return orderMnName;
    }

    public void setOrderMnName(String orderMnName) {
        this.orderMnName = orderMnName;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderAmnt() {
        return orderAmnt;
    }

    public void setOrderAmnt(String orderAmnt) {
        this.orderAmnt = orderAmnt;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;

    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }
}