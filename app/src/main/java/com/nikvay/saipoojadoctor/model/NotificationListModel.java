package com.nikvay.saipoojadoctor.model;

public class NotificationListModel {
  private String patient_id;
  private String patient_name;
  private String description;
  private String date;
  private String status;


    public NotificationListModel(String patient_id, String patient_name, String description, String date,String status) {
        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
