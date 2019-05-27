package com.nikvay.doctorapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SuccessModel {

     private  String  error_code;
     private  String  msg;

    @SerializedName("doctor_login")
     ArrayList<DoctorModel> doctorModelArrayList;


    @SerializedName("service")
    ArrayList<ServiceModel> serviceModelArrayList;

    @SerializedName("patient")
    ArrayList<PatientModel> patientModelArrayList;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DoctorModel> getDoctorModelArrayList() {
        return doctorModelArrayList;
    }

    public void setDoctorModelArrayList(ArrayList<DoctorModel> doctorModelArrayList) {
        this.doctorModelArrayList = doctorModelArrayList;
    }

    public ArrayList<ServiceModel> getServiceModelArrayList() {
        return serviceModelArrayList;
    }

    public void setServiceModelArrayList(ArrayList<ServiceModel> serviceModelArrayList) {
        this.serviceModelArrayList = serviceModelArrayList;
    }

    public ArrayList<PatientModel> getPatientModelArrayList() {
        return patientModelArrayList;
    }

    public void setPatientModelArrayList(ArrayList<PatientModel> patientModelArrayList) {
        this.patientModelArrayList = patientModelArrayList;
    }
}
