package com.nikvay.saipoojadoctor.interfaceutils;

import com.nikvay.saipoojadoctor.model.DoctorListModel;

public interface SelectMultipleDoctorInterface {
    public void selectedServiceId(String mID);
    public void selecteddoctorName(DoctorListModel doctorListModel);
    public void subSelectedProduct(DoctorListModel doctorListModel);
}
