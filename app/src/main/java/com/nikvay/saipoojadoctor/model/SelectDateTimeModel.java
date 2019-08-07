package com.nikvay.saipoojadoctor.model;

public class SelectDateTimeModel {

    private String time;
    private String status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
