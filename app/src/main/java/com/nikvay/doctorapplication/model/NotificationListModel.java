package com.nikvay.doctorapplication.model;

public class NotificationListModel {
  private String patient_id;
  private String name;
  private String title;
  private String description;
  private String date;


    public NotificationListModel(String patient_id, String name, String title, String description, String date) {
        this.patient_id = patient_id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.date = date;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
