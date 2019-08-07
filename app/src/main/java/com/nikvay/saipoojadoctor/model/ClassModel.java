package com.nikvay.saipoojadoctor.model;

import java.io.Serializable;

public class ClassModel implements Serializable {
    private String class_id;
    private String name;
    private String description;
    private String duration;
    private String seats;
    private String cost;
    private String date;
    private int session_count;
    private String date_string;
    private String doctor_name;

    public ClassModel(String class_id, String name, String duration, String seats, String cost, String date, int session_count,String date_string,String doctor_name)
    {
        this.class_id = class_id;
        this.name = name;
        this.description = duration;
        this.seats = seats;
        this.cost = cost;
        this.date = date;
        this.session_count=session_count;
        this.date_string=date_string;
        this.doctor_name=doctor_name;
    }

    public String getDoctor_name()
    {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name)
    {
        this.doctor_name = doctor_name;
    }

    public ClassModel() {
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
    public void setSession_count(int session_count)
    {
        this.session_count=session_count;
    }
    public int getSession_count()
    {
        return session_count;
    }

    public String getDate_string()
    {
        return date_string;
    }

    public void setDate_string(String date_string)
    {
        this.date_string = date_string;
    }

}
