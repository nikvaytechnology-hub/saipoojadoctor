package com.nikvay.saipooja_doctor.interfaceutils;

import com.nikvay.saipooja_doctor.model.DoctorListModel;

public interface SelectMultipleDoctorInterface {
    public void selectedServiceId(String mID);
    public void selecteddoctorName(DoctorListModel doctorListModel);
    public void subSelectedProduct(DoctorListModel doctorListModel);
}
