package com.nikvay.saipoojadoctor.model;

import java.io.Serializable;

public class EnquiryListModel implements Serializable {


    String enquiry_id;
    String title;
    String description;
    String enquiry_date;
    String enquiry_time;
    String reply;
    String reply_date;
    String reply_time;
    String name;
    String phone_no;
    String email;

    public EnquiryListModel(String enquiry_id, String title, String description, String enquiry_date, String enquiry_time, String reply, String reply_date, String reply_time, String name, String phone_no, String email) {
        this.enquiry_id = enquiry_id;
        this.title = title;
        this.description = description;
        this.enquiry_date = enquiry_date;
        this.enquiry_time = enquiry_time;
        this.reply = reply;
        this.reply_date = reply_date;
        this.reply_time = reply_time;
        this.name = name;
        this.phone_no = phone_no;
        this.email = email;
    }

    public String getEnquiry_id() {
        return enquiry_id;
    }

    public void setEnquiry_id(String enquiry_id) {
        this.enquiry_id = enquiry_id;
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

    public String getEnquiry_date() {
        return enquiry_date;
    }

    public void setEnquiry_date(String enquiry_date) {
        this.enquiry_date = enquiry_date;
    }

    public String getEnquiry_time() {
        return enquiry_time;
    }

    public void setEnquiry_time(String enquiry_time) {
        this.enquiry_time = enquiry_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply_date() {
        return reply_date;
    }

    public void setReply_date(String reply_date) {
        this.reply_date = reply_date;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }
}
