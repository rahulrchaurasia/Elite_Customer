package com.pb.elite.core.requestmodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IN-RB on 10-08-2018.
 */

public class InsertOrderRequestEntity implements Parcelable  {


    /**
     * prodid : 109
     * userid : 14
     * rto_id : MH 03
     * transaction_id : tran54356
     * subscription :
     * vehicleno :
     * pucexpirydate :
     * cityid : 1276
     * amount : 0
     */

    private String prodid;
    private String prodName;
    private String userid;
    private String rto_id;

    private String transaction_id;
    private String subscription;
    private String vehicleno;
    private String pucexpirydate;
    private String cityid;
    private String amount;
    private String payment_status;


    protected InsertOrderRequestEntity(Parcel in) {
        prodid = in.readString();
        prodName = in.readString();
        userid = in.readString();
        rto_id = in.readString();
        transaction_id = in.readString();
        subscription = in.readString();
        vehicleno = in.readString();
        pucexpirydate = in.readString();
        cityid = in.readString();
        amount = in.readString();
        payment_status = in.readString();
    }

    public static final Creator<InsertOrderRequestEntity> CREATOR = new Creator<InsertOrderRequestEntity>() {
        @Override
        public InsertOrderRequestEntity createFromParcel(Parcel in) {
            return new InsertOrderRequestEntity(in);
        }

        @Override
        public InsertOrderRequestEntity[] newArray(int size) {
            return new InsertOrderRequestEntity[size];
        }
    };

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getPucexpirydate() {
        return pucexpirydate;
    }

    public void setPucexpirydate(String pucexpirydate) {
        this.pucexpirydate = pucexpirydate;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }



    public InsertOrderRequestEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prodid);
        dest.writeString(prodName);
        dest.writeString(userid);
        dest.writeString(rto_id);
        dest.writeString(transaction_id);
        dest.writeString(subscription);
        dest.writeString(vehicleno);
        dest.writeString(pucexpirydate);
        dest.writeString(cityid);
        dest.writeString(amount);
        dest.writeString(payment_status);
    }
}
