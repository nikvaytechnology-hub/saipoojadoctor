package com.nikvay.saipoojadoctor.model;

public class DrawerItem {
    int logoName;
    String categoryType;

    public DrawerItem(int logoName, String categoryType) {
        this.logoName=logoName;
        this.categoryType=categoryType;
    }

    public int getLogoName() {
        return logoName;
    }

    public void setLogoName(int logoName) {
        this.logoName = logoName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }


}
