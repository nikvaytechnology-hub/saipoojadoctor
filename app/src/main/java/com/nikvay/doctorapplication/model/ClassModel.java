package com.nikvay.doctorapplication.model;

import java.io.Serializable;

public class ClassModel implements Serializable {
    private String id;
    private String class_name;
    private String duration;
    private String seats;
    private String cost;

    public ClassModel(String id, String class_name, String duration, String seats, String cost) {
        this.id = id;
        this.class_name = class_name;
        this.duration = duration;
        this.seats = seats;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
}
