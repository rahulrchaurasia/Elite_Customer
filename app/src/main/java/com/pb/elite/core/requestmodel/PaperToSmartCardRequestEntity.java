package com.pb.elite.core.requestmodel;

/**
 * Created by Nilesh Birhade on 18-12-2018.
 */

public class PaperToSmartCardRequestEntity {


    /**
     * amount : 0
     * cityid : 1852
     * payment_status : 1
     * prodid : 132
     * rto_id : MH12
     * transaction_id : 1232
     * vehicleno : MH12SH3454
     * userid : 22
     * pincode : 400070
     * DL_No : 0989
     * DL_Correct_name : test
     * DL_DOB : 13/09/1994
     */

    private String amount;
    private String cityid;
    private String payment_status;
    private String prodid;
    private String rto_id;
    private String transaction_id;
    private String vehicleno;
    private String userid;
    private String pincode;
    private String DL_No;
    private String DL_Correct_name;
    private String DL_DOB;

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

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
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

    public String getDL_No() {
        return DL_No;
    }

    public void setDL_No(String DL_No) {
        this.DL_No = DL_No;
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
}
