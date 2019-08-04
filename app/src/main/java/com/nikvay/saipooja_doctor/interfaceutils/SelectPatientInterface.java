package com.nikvay.saipooja_doctor.interfaceutils;

import com.nikvay.saipooja_doctor.model.PatientModel;
import com.nikvay.saipooja_doctor.model.ServiceModel;

public interface SelectPatientInterface {
    public void getPatientDetail(PatientModel patientModel);
    public void getServiceDetail(ServiceModel serviceModel);
}
