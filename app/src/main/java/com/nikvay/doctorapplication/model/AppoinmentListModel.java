package com.nikvay.doctorapplication.model;

public class AppoinmentListModel {
    String week_of_apppoinment;
    String day_of_month;
    String date_of_Time;
    String appoinment_service;
    String appoinment_coast;
    String doctor_name;
    String appoinment_Duration;

    public AppoinmentListModel(String week_of_apppoinment, String day_of_month, String date_of_Time, String doctor_name, String appoinment_Duration, String appoinment_service, String appoinment_coast)
    {
        this.week_of_apppoinment = week_of_apppoinment;
        this.day_of_month = day_of_month;
        this.date_of_Time = date_of_Time;
        this.doctor_name = doctor_name;
        this.appoinment_Duration = appoinment_Duration;
        this.appoinment_service = appoinment_service;
        this.appoinment_coast = appoinment_coast;
    }



    public String getWeek_of_apppoinment() {
        return week_of_apppoinment;
    }

    public void setWeek_of_apppoinment(String week_of_apppoinment) {
        this.week_of_apppoinment = week_of_apppoinment;
    }

    public String getDay_of_month() {
        return day_of_month;
    }

    public void setDay_of_month(String day_of_month) {
        this.day_of_month = day_of_month;
    }

    public String getDate_of_Time() {
        return date_of_Time;
    }

    public void setDate_of_Time(String date_of_Time) {
        this.date_of_Time = date_of_Time;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getAppoinment_Duration() {
        return appoinment_Duration;
    }

    public void setAppoinment_Duration(String appoinment_Duration) {
        this.appoinment_Duration = appoinment_Duration;
    }

    public String getAppoinment_service() {
        return appoinment_service;
    }

    public void setAppoinment_service(String appoinment_service) {
        this.appoinment_service = appoinment_service;
    }

    public String getAppoinment_coast() {
        return appoinment_coast;
    }

    public void setAppoinment_coast(String appoinment_coast) {
        this.appoinment_coast = appoinment_coast;
    }


}
