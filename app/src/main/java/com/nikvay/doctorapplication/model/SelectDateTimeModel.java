package com.nikvay.doctorapplication.model;

public class SelectDateTimeModel {

    private String time;
    boolean isSelected;

    public SelectDateTimeModel(String time) {
        this.time=time;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
