package com.nikvay.doctorapplication.model;

import java.io.Serializable;

public class ServiceModel  implements Serializable {


    String doctor_id;
    String service_id;
    String name;
    String s_name;
    String service_cost;
    String service_time;
    String status;
    String description;

    public ServiceModel(String doctor_id, String service_id, String name, String s_name,
                        String service_cost, String service_time, String status, String description) {
        this.doctor_id = doctor_id;
        this.service_id = service_id;
        this.name = name;
        this.s_name = s_name;
        this.service_cost = service_cost;
        this.service_time = service_time;
        this.status = status;
        this.description = description;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getService_cost() {
        return service_cost;
    }

    public void setService_cost(String service_cost) {
        this.service_cost = service_cost;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
