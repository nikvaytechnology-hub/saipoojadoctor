package com.nikvay.saipooja_doctor.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SessionListModel  implements Serializable {


    private String session_id;
    private String date;
    private String time;
    private String label;
    private String cost;
    private String no_of_seats;
    private String name;
    private String name_class;
    private String doctor_id;


    public SessionListModel(String session_id, String date, String time, String label, String cost, String no_of_seats, String name, String name_class,String doctor_id) {
        this.session_id = session_id;
        this.date = date;
        this.time = time;
        this.label = label;
        this.cost = cost;
        this.no_of_seats = no_of_seats;
        this.name = name;
        this.name_class = name_class;
        this.doctor_id=doctor_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getNo_of_seats() {
        return no_of_seats;
    }

    public void setNo_of_seats(String no_of_seats) {
        this.no_of_seats = no_of_seats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_class() {
        return name_class;
    }

    public void setName_class(String name_class) {
        this.name_class = name_class;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }
}
