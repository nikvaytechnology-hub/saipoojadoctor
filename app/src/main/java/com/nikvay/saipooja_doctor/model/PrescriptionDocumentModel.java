package com.nikvay.saipooja_doctor.model;

import java.io.Serializable;

public class PrescriptionDocumentModel implements Serializable {

    private String document_id;
    private String title;
    private String document_name;
    private String uploaded_date;

    public PrescriptionDocumentModel(String document_id, String title, String document_name, String uploaded_date) {
        this.document_id = document_id;
        this.title = title;
        this.document_name = document_name;
        this.uploaded_date = uploaded_date;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getUploaded_date() {
        return uploaded_date;
    }

    public void setUploaded_date(String uploaded_date) {
        this.uploaded_date = uploaded_date;
    }
}
