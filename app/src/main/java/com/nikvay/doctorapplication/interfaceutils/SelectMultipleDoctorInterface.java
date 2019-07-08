package com.nikvay.doctorapplication.interfaceutils;

import com.nikvay.doctorapplication.model.DoctorListModel;

public interface SelectMultipleDoctorInterface {
    public void selectedServiceId(String mID);
    public void selecteddoctorName(DoctorListModel doctorListModel);
    public void subSelectedProduct(DoctorListModel doctorListModel);
}
