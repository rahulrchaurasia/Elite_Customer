package com.pb.elite.core.requestmodel;

/**
 * Created by Nilesh Birhade on 18-12-2018.
 */

public class DriverDLVerificationRequestEntity {


    /**
     * amount : 0
     * cityid : 1852
     * payment_status : 1
     * prodid : 132
     * rto_id : MH12
     * transaction_id : 1232
     * userid : 22
     * pincode : 400070
     * DL_Correct_name : honda
     * DL_DOB : 12/03/1234
     * DL_No : 6767867
     */

    private String amount;
    private String cityid;
    private String payment_status;
    private String prodid;
    private String rto_id;
    private String transaction_id;
    private String userid;
    private String pincode;
    private String DL_Correct_name;
    private String DL_DOB;
    private String DL_No;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getRto_id() {
        return rto_id;
    }

    public void setRto_id(String rto_id) {
        this.rto_id = rto_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDL_Correct_name() {
        return DL_Correct_name;
    }

    public void setDL_Correct_name(String DL_Correct_name) {
        this.DL_Correct_name = DL_Correct_name;
    }

    public String getDL_DOB() {
        return DL_DOB;
    }

    public void setDL_DOB(String DL_DOB) {
        this.DL_DOB = DL_DOB;
    }

    public String getDL_No() {
        return DL_No;
    }

    public void setDL_No(String DL_No) {
        this.DL_No = DL_No;
    }
}
