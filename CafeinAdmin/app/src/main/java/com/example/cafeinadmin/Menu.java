package com.example.cafeinadmin;

public class Menu {
    private String menuName, menuPrice,menuImage;


    public Menu(String menuName, String menuPrice,String menuImage) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuImage= menuImage;


    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuImage() {
        return menuImage;
    }


    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }
}