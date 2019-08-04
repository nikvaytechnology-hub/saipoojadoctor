package com.nikvay.saipooja_doctor.model;

public class PatientAppointmentHistoryModel {


    private String date, time, label, comment, service_name, service_cost, service_time, name, email, address, phone_no;


    public PatientAppointmentHistoryModel(String date, String time, String label, String comment, String service_name, String service_cost, String service_time, String name, String email, String address, String phone_no) {
        this.date = date;
        this.time = time;
        this.label = label;
        this.comment = comment;
        this.service_name = service_name;
        this.service_cost = service_cost;
        this.service_time = service_time;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone_no = phone_no;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
