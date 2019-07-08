package com.nikvay.doctorapplication.model;

import java.io.Serializable;

public class MedicineListModel implements Serializable
{
   String medicineName;
   String MedicineTest;
   int index;

    public MedicineListModel(int index, String medicineName, String medicineTest) {
        this.medicineName = medicineName;
        this.MedicineTest = medicineTest;
        this.index = index;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineTest() {
        return MedicineTest;
    }

    public void setMedicineTest(String medicineTest) {
        MedicineTest = medicineTest;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
