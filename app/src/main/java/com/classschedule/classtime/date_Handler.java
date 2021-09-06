package com.classschedule.classtime;

public class date_Handler {

    private String classScheduleDate;

    date_Handler(){

    }

    public date_Handler(String classScheduleDate) {
        this.classScheduleDate = classScheduleDate;
    }

    public String getClassScheduleDate() {
        return classScheduleDate;
    }

    public void setClassScheduleDate(String classScheduleDate) {
        this.classScheduleDate = classScheduleDate;
    }
}
