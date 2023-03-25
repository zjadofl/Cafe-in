package com.example.cafeinadmin;

public class Order {

    private String number; //주문 번호

    public Order() {}

    public Order(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
