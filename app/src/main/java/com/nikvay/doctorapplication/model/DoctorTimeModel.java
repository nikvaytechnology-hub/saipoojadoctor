package com.nikvay.doctorapplication.model;

import java.io.Serializable;

public class DoctorTimeModel implements Serializable {
    String Day_Id;
    String Day;
    String timeSlot;
    String startTime;
    String endTime;
    private boolean isSelected = false;


    public String getDay_Id() {
        return Day_Id;
    }

    public void setDay_Id(String day_Id) {
        Day_Id = day_Id;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
