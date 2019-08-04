package com.nikvay.saipooja_doctor.model;

public class SessionPatientExistModel
{
    String patient_id,doctor_id,name,email,gender,phone_no,status;
    boolean isSelected;

    public SessionPatientExistModel(String patient_id, String doctor_id, String name, String email, String gender, String phone_no, String status)
    {
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phone_no = phone_no;
        this.status = status;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
