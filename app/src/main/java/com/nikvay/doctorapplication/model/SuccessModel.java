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
     private  String todays_appointment_count;
     private  String img_base_url;


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

    @SerializedName("classList")
    ArrayList<ClassModel> classModelArrayList;


    @SerializedName("AptNotificetionlist")
    ArrayList<NotificationListModel> notificationListModelArrayList;

    @SerializedName("listpPaymentHistory")
    ArrayList<PaymentDetailModel>paymentDetailModelArrayList ;

    @SerializedName("appointment_list_for_doctor")
    ArrayList<PatientAppointmentHistoryModel> patientAppointmentHistoryModelArrayList;



    @SerializedName("listPrescription")
    ArrayList<PatientPrescriptionHistoryModel> patientPrescriptionHistoryModelArrayList;

    @SerializedName("list_enquiry")
    ArrayList<EnquiryListModel> enquiryListModelArrayList;

    @SerializedName("document_list")
    ArrayList<PrescriptionDocumentModel> prescriptionDocumentModelArrayList;


    @SerializedName("doctor_list")
    ArrayList<DoctorListModel>doctorListModelArrayList ;

    @SerializedName("department_list")
    ArrayList<DepartmentModel> departmentModelArrayList;


    @SerializedName("admin_patient_list")
    ArrayList<PatientModel> patientModelArrayListAdmin;


    @SerializedName("admin_service_list")
    ArrayList<ServiceListModel>serviceListModelArrayList;



    public ArrayList<PaymentDetailModel> getPaymentDetailModelArrayList() {
        return paymentDetailModelArrayList;
    }

    public void setPaymentDetailModelArrayList(ArrayList<PaymentDetailModel> paymentDetailModelArrayList) {
        this.paymentDetailModelArrayList = paymentDetailModelArrayList;
    }


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

    public ArrayList<ClassModel> getClassModelArrayList() {
        return classModelArrayList;
    }

    public void setClassModelArrayList(ArrayList<ClassModel> classModelArrayList) {
        this.classModelArrayList = classModelArrayList;
    }

    public ArrayList<NotificationListModel> getNotificationListModelArrayList() {
        return notificationListModelArrayList;
    }

    public void setNotificationListModelArrayList(ArrayList<NotificationListModel> notificationListModelArrayList) {
        this.notificationListModelArrayList = notificationListModelArrayList;
    }

    public ArrayList<PatientAppointmentHistoryModel> getPatientAppointmentHistoryModelArrayList() {
        return patientAppointmentHistoryModelArrayList;
    }

    public void setPatientAppointmentHistoryModelArrayList(ArrayList<PatientAppointmentHistoryModel> patientAppointmentHistoryModelArrayList) {
        this.patientAppointmentHistoryModelArrayList = patientAppointmentHistoryModelArrayList;
    }

    public ArrayList<PatientPrescriptionHistoryModel> getPatientPrescriptionHistoryModelArrayList() {
        return patientPrescriptionHistoryModelArrayList;
    }

    public void setPatientPrescriptionHistoryModelArrayList(ArrayList<PatientPrescriptionHistoryModel> patientPrescriptionHistoryModelArrayList) {
        this.patientPrescriptionHistoryModelArrayList = patientPrescriptionHistoryModelArrayList;
    }

    public ArrayList<EnquiryListModel> getEnquiryListModelArrayList() {
        return enquiryListModelArrayList;
    }

    public void setEnquiryListModelArrayList(ArrayList<EnquiryListModel> enquiryListModelArrayList) {
        this.enquiryListModelArrayList = enquiryListModelArrayList;
    }

    public String getTodays_appointment_count() {
        return todays_appointment_count;
    }

    public void setTodays_appointment_count(String todays_appointment_count) {
        this.todays_appointment_count = todays_appointment_count;
    }

    public ArrayList<PrescriptionDocumentModel> getPrescriptionDocumentModelArrayList() {
        return prescriptionDocumentModelArrayList;
    }

    public void setPrescriptionDocumentModelArrayList(ArrayList<PrescriptionDocumentModel> prescriptionDocumentModelArrayList) {
        this.prescriptionDocumentModelArrayList = prescriptionDocumentModelArrayList;
    }

    public String getImg_base_url() {
        return img_base_url;
    }

    public void setImg_base_url(String img_base_url) {
        this.img_base_url = img_base_url;
    }

    public ArrayList<DoctorListModel> getDoctorListModelArrayList() {
        return doctorListModelArrayList;
    }

    public void setDoctorListModelArrayList(ArrayList<DoctorListModel> doctorListModelArrayList) {
        this.doctorListModelArrayList = doctorListModelArrayList;
    }

    public ArrayList<DepartmentModel> getDepartmentModelArrayList() {
        return departmentModelArrayList;
    }

    public void setDepartmentModelArrayList(ArrayList<DepartmentModel> departmentModelArrayList) {
        this.departmentModelArrayList = departmentModelArrayList;
    }

    public ArrayList<PatientModel> getPatientModelArrayListAdmin() {
        return patientModelArrayListAdmin;
    }

    public void setPatientModelArrayListAdmin(ArrayList<PatientModel> patientModelArrayListAdmin) {
        this.patientModelArrayListAdmin = patientModelArrayListAdmin;
    }

    public ArrayList<ServiceListModel> getServiceListModelArrayList() {
        return serviceListModelArrayList;
    }

    public void setServiceListModelArrayList(ArrayList<ServiceListModel> serviceListModelArrayList) {
        this.serviceListModelArrayList = serviceListModelArrayList;
    }

    public void setPatientModelArrayList(ArrayList<PatientModel> patientModelArrayList) {
        this.patientModelArrayList = patientModelArrayList;





    }
}
