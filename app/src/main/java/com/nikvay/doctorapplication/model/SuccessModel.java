package com.nikvay.doctorapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SuccessModel {

     private  String  error_code;
     private  String  msg;
     private  String appointment_for_Pending;
     private  String appointment_for_Confirmed;
     private  String appointment_for_Cancelled;
     private  String appointment_for_Completed;

    @SerializedName("doctor_login")
     ArrayList<DoctorModel> doctorModelArrayList;


    @SerializedName("service")
    ArrayList<ServiceModel> serviceModelArrayList;

    @SerializedName("patient")
    ArrayList<PatientModel> patientModelArrayList;

    @SerializedName("time")
    ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList;


    @SerializedName("appointment")
    ArrayList<AppoinmentListModel> appoinmentListModelArrayList;


    public ArrayList<SelectDateTimeModel> getSelectDateTimeModelArrayList() {
        return selectDateTimeModelArrayList;
    }

    public void setSelectDateTimeModelArrayList(ArrayList<SelectDateTimeModel> selectDateTimeModelArrayList) {
        this.selectDateTimeModelArrayList = selectDateTimeModelArrayList;
    }



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

    public ArrayList<AppoinmentListModel> getAppoinmentListModelArrayList() {
        return appoinmentListModelArrayList;
    }

    public void setAppoinmentListModelArrayList(ArrayList<AppoinmentListModel> appoinmentListModelArrayList) {
        this.appoinmentListModelArrayList = appoinmentListModelArrayList;
    }

    public String getAppointment_for_Pending() {
        return appointment_for_Pending;
    }

    public void setAppointment_for_Pending(String appointment_for_Pending) {
        this.appointment_for_Pending = appointment_for_Pending;
    }

    public String getAppointment_for_Confirmed() {
        return appointment_for_Confirmed;
    }

    public void setAppointment_for_Confirmed(String appointment_for_Confirmed) {
        this.appointment_for_Confirmed = appointment_for_Confirmed;
    }

    public String getAppointment_for_Cancelled() {
        return appointment_for_Cancelled;
    }

    public void setAppointment_for_Cancelled(String appointment_for_Cancelled) {
        this.appointment_for_Cancelled = appointment_for_Cancelled;
    }

    public String getAppointment_for_Completed() {
        return appointment_for_Completed;
    }

    public void setAppointment_for_Completed(String appointment_for_Completed) {
        this.appointment_for_Completed = appointment_for_Completed;
    }

    public void setPatientModelArrayList(ArrayList<PatientModel> patientModelArrayList) {
        this.patientModelArrayList = patientModelArrayList;




    }
}
