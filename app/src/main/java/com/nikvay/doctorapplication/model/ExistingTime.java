package com.nikvay.doctorapplication.model;

public class ExistingTime
{
    String day,startTime,endTime,timeSlot,dayStatus;

    public ExistingTime(String day, String startTime, String endTime, String timeSlot, String dayStatus)
    {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSlot = timeSlot;
        this.dayStatus = dayStatus;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getDayStatus() {
        return dayStatus;
    }

    public void setDayStatus(String dayStatus) {
        this.dayStatus = dayStatus;
    }
}
