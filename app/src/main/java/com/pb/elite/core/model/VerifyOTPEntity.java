package com.pb.elite.core.model;

public  class VerifyOTPEntity {
    /**
     * message : OTP Generated succesfully
     * status : Success
     * status_code : 0
     * Data : 874382
     * Message : OTP Generated succesfully
     * SavedStatus : 1
     */

    private String message;
    private String status;
    private int status_code;
    private int Data;
    private String Message;
    private int SavedStatus;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public int getData() {
        return Data;
    }

    public void setData(int Data) {
        this.Data = Data;
    }

    public String getMessageX() {
        return Message;
    }

    public void setMessageX(String Message) {
        this.Message = Message;
    }

    public int getSavedStatus() {
        return SavedStatus;
    }

    public void setSavedStatus(int SavedStatus) {
        this.SavedStatus = SavedStatus;
    }
    /**
         * SavedStatus : 2
         * Message : User already exists
         */


    }