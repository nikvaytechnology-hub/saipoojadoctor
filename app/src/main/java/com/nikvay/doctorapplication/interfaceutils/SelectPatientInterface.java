package com.nikvay.doctorapplication.interfaceutils;

import com.nikvay.doctorapplication.model.PatientModel;
import com.nikvay.doctorapplication.model.ServiceModel;

public interface SelectPatientInterface {
    public void getPatientDetail(PatientModel patientModel);
    public void getServiceDetail(ServiceModel serviceModel);
}
