package com.rb.elite.core.requestmodel;

import android.os.Parcel;
import android.os.Parcelable;

public  class ExtrarequestEntity implements Parcelable{
    /**
     * MakeNo : MH02DV3282
     * ModelNo : 87564KJGH87SA4
     */
    private String VehicleNo;
    private String DrivingLic;



    private String MakeNo;
    private String ModelNo;

    public ExtrarequestEntity() {
    }

    protected ExtrarequestEntity(Parcel in) {
        MakeNo = in.readString();
        ModelNo = in.readString();
        VehicleNo = in.readString();
        DrivingLic = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MakeNo);
        dest.writeString(ModelNo);
        dest.writeString(VehicleNo);
        dest.writeString(DrivingLic);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getDrivingLic() {
        return DrivingLic;
    }

    public void setDrivingLic(String drivingLic) {
        DrivingLic = drivingLic;
    }


}


