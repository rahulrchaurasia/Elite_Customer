package com.pb.elite.core.requestmodel;

import android.os.Parcel;
import android.os.Parcelable;

public  class ExtrarequestEntity implements Parcelable{
    /**
     * MakeNo : MH02DV3282
     * ModelNo : 87564KJGH87SA4
     */

    private String MakeNo;
    private String ModelNo;
    private String VehicleNo;


    protected ExtrarequestEntity(Parcel in) {
        MakeNo = in.readString();
        ModelNo = in.readString();
        VehicleNo = in.readString();
    }

    public static final Creator<ExtrarequestEntity> CREATOR = new Creator<ExtrarequestEntity>() {
        @Override
        public ExtrarequestEntity createFromParcel(Parcel in) {
            return new ExtrarequestEntity(in);
        }

        @Override
        public ExtrarequestEntity[] newArray(int size) {
            return new ExtrarequestEntity[size];
        }
    };

    public ExtrarequestEntity() {
    }

    public String getMakeNo() {
        return MakeNo;
    }

    public void setMakeNo(String makeNo) {
        MakeNo = makeNo;
    }

    public String getModelNo() {
        return ModelNo;
    }

    public void setModelNo(String modelNo) {
        ModelNo = modelNo;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MakeNo);
        dest.writeString(ModelNo);
        dest.writeString(VehicleNo);
    }
}


