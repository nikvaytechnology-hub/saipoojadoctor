package com.nikvay.doctorapplication.model;

public class SessionListModel {
    private String date;
    private String time;
    private String class_name;


    public SessionListModel(String date, String time, String class_name) {
        this.date = date;
        this.time = time;
        this.class_name = class_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
