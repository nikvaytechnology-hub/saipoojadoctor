package com.nikvay.doctorapplication.model;

import java.io.Serializable;

public class PatientModel implements Serializable {

    private  String patient_id;
    private  String name;
    private  String email;
    private  String phone_no;
    private  String address;
    private  String appointment;

    public PatientModel(String patient_id, String name, String email, String phone_no, String address, String appointment) {
        this.patient_id = patient_id;
        this.name = name;
        this.email = email;
        this.phone_no = phone_no;
        this.address = address;
        this.appointment = appointment;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
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

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }
}
