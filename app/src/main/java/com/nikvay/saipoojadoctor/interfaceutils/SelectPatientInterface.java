package com.nikvay.saipoojadoctor.interfaceutils;

import com.nikvay.saipoojadoctor.model.PatientModel;
import com.nikvay.saipoojadoctor.model.ServiceModel;

public interface SelectPatientInterface {
    public void getPatientDetail(PatientModel patientModel);
    public void getServiceDetail(ServiceModel serviceModel);
}
