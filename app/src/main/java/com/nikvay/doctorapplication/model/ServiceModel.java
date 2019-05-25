package com.nikvay.doctorapplication.model;

import java.io.Serializable;

public class ServiceModel  implements Serializable {
    private String id;
    private String service_name;
    private String  duration;
    private String cost;

    public ServiceModel(String id, String service_name, String duration, String cost) {
        this.id = id;
        this.service_name = service_name;
        this.duration = duration;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
