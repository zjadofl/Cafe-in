package com.example.cafein;

public class Menu {

    private String menuNum, menuName, menuImg, menuPrice;


    public Menu() {

    }

    public Menu(String menuNum, String menuName, String menuImg, String menuPrice) {
        this.menuNum = menuNum;
        this.menuName = menuName;
        this.menuImg= menuImg;
        this.menuPrice = menuPrice;

    }

    public String getMenuNum() {
        return menuNum;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuImg() {
        return menuImg;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuNum(String menuNum) {
        this.menuNum = menuNum;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }


}
