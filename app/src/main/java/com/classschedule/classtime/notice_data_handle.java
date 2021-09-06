package com.classschedule.classtime;

public class notice_data_handle {

    private String notDate,notTitle,notMessage,notCatagory;

    notice_data_handle(){

    }

    public notice_data_handle(String notDate, String notTitle, String notMessage, String notCatagory) {
        this.notDate = notDate;
        this.notTitle = notTitle;
        this.notMessage = notMessage;
        this.notCatagory = notCatagory;
    }

    public String getNotDate() {
        return notDate;
    }

    public void setNotDate(String notDate) {
        this.notDate = notDate;
    }

    public String getNotTitle() {
        return notTitle;
    }

    public void setNotTitle(String notTitle) {
        this.notTitle = notTitle;
    }

    public String getNotMessage() {
        return notMessage;
    }

    public void setNotMessage(String notMessage) {
        this.notMessage = notMessage;
    }

    public String getNotCatagory() {
        return notCatagory;
    }

    public void setNotCatagory(String notCatagory) {
        this.notCatagory = notCatagory;
    }
}
