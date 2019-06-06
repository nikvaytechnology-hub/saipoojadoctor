package com.nikvay.doctorapplication.model;

import java.io.Serializable;

public class ClassModel implements Serializable {
    private String class_id;
    private String name;
    private String description;
    private String duration;
    private String seats;
    private String cost;
    private String date;

    public ClassModel(String class_id, String name, String duration, String seats, String cost,String date) {
        this.class_id = class_id;
        this.name = name;
        this.description = duration;
        this.seats = seats;
        this.cost = cost;
        this.date = date;
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
}
