package com.nikvay.doctorapplication.model;

import java.io.Serializable;

public class AppointmentListModel implements Serializable {
   private  String appointment_id;
   private  String date;
   private  String time;
   private  String service_id;
   private  String label;
   private  String s_name;
   private  String service_cost;
   private  String patient_id;
   private  String name;
   private  String email;
   private  String address;
   private  String phone_no;
   private  String service_time;
   private  String comment;


    public AppointmentListModel(String appointment_id, String date, String time, String service_id, String label, String s_name, String service_cost, String patient_id, String name, String email, String address, String phone_no, String service_time, String comment) {
        this.appointment_id = appointment_id;
        this.date = date;
        this.time = time;
        this.service_id = service_id;
        this.label = label;
        this.s_name = s_name;
        this.service_cost = service_cost;
        this.patient_id = patient_id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone_no = phone_no;
        this.service_time = service_time;
        this.comment = comment;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
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

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
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

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
