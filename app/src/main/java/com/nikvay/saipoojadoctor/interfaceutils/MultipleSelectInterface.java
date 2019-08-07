package com.nikvay.saipoojadoctor.interfaceutils;

import com.nikvay.saipoojadoctor.model.SessionPatientExistModel;

public interface MultipleSelectInterface
{
    void getSessionPatientDetail(SessionPatientExistModel sessionPatientExistModel);
    void removePatientAdded(SessionPatientExistModel sessionPatientExistModel);

}
