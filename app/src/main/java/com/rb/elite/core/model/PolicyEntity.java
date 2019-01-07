package com.rb.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

public  class PolicyEntity implements Parcelable {

    /**
     * PolicyNumber : 110521823110005216
     * ProductCode : 2311
     * RiskEndDate : 21 Jan 2019
     * RiskStartDate : 22 Jan 2018
     * InsuredName : RUDRAGOUDA S PATIL
     * VehicleNumber : KA04ML6747
     * Make : SKODA
     * Model : RAPID
     * PolicyStatus : Active
     * ResponseStatus : Policy is eligible for ELITE services
     */

    private String PolicyNumber;
    private String ProductCode;
    private String RiskEndDate;
    private String RiskStartDate;
    private String InsuredName;
    private String VehicleNumber;
    private String Make;
    private String Model;
    private String PolicyStatus;
    private String ResponseStatus;

    protected PolicyEntity(Parcel in) {
        PolicyNumber = in.readString();
        ProductCode = in.readString();
        RiskEndDate = in.readString();
        RiskStartDate = in.readString();
        InsuredName = in.readString();
        VehicleNumber = in.readString();
        Make = in.readString();
        Model = in.readString();
        PolicyStatus = in.readString();
        ResponseStatus = in.readString();
    }

    public static final Creator<PolicyEntity> CREATOR = new Creator<PolicyEntity>() {
        @Override
        public PolicyEntity createFromParcel(Parcel in) {
            return new PolicyEntity(in);
        }

        @Override
        public PolicyEntity[] newArray(int size) {
            return new PolicyEntity[size];
        }
    };

    public String getPolicyNumber() {
        return PolicyNumber;
    }

    public void setPolicyNumber(String PolicyNumber) {
        this.PolicyNumber = PolicyNumber;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String ProductCode) {
        this.ProductCode = ProductCode;
    }

    public String getRiskEndDate() {
        return RiskEndDate;
    }

    public void setRiskEndDate(String RiskEndDate) {
        this.RiskEndDate = RiskEndDate;
    }

    public String getRiskStartDate() {
        return RiskStartDate;
    }

    public void setRiskStartDate(String RiskStartDate) {
        this.RiskStartDate = RiskStartDate;
    }

    public String getInsuredName() {
        return InsuredName;
    }

    public void setInsuredName(String InsuredName) {
        this.InsuredName = InsuredName;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String VehicleNumber) {
        this.VehicleNumber = VehicleNumber;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String Make) {
        this.Make = Make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public String getPolicyStatus() {
        return PolicyStatus;
    }

    public void setPolicyStatus(String PolicyStatus) {
        this.PolicyStatus = PolicyStatus;
    }

    public String getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(String ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PolicyNumber);
        dest.writeString(ProductCode);
        dest.writeString(RiskEndDate);
        dest.writeString(RiskStartDate);
        dest.writeString(InsuredName);
        dest.writeString(VehicleNumber);
        dest.writeString(Make);
        dest.writeString(Model);
        dest.writeString(PolicyStatus);
        dest.writeString(ResponseStatus);
    }
}