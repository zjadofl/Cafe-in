package com.example.cafein;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderHistoryData {
    private String orderNum,menuNum,orderMnName, orderDate, orderAmnt, orderPrice, option,price,img,type;
    SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    Calendar time = Calendar.getInstance();

    public OrderHistoryData(String orderNum,String menuNum,String menuName,String price,String img,String type, String orderDate, String orderAmnt, String orderPrice, String option) {
        this.orderNum = orderNum;
        this.menuNum=menuNum;
        this.orderMnName = menuName;
        this.price= price;
        this.img= img;
        this.type= type;
        this.orderDate = orderDate;
        this.orderAmnt = orderAmnt;
        this.orderPrice = orderPrice;
        this.option = option;
    }

    public String getMenuNum() {
        return menuNum;
    }

    public void setMenuNum(String menuNum) {
        this.menuNum = menuNum;
    }

    public String getMenuPrice() {
        return price;
    }

    public void setMenuPrice(String price) {
        this.price = price;
    }

    public String getMenuType() {
        return type;
    }

    public void setMenuType(String type) {
        this.type = type;
    }

    public String getMenuImg() {
        return img;
    }

    public void setMenuImg(String img) {
        this.img = img;
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

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}