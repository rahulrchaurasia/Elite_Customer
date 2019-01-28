package com.rb.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;



public class CarMasterEntity   implements Parcelable {


    /**
     * V_Id : 9
     * CreatedOn : 2147483647
     * Cubic_Capacity : 1798
     * Description : AUDI A4 1.8 MULTITRONIC
     * ExShoroomPrice : 2656000
     * Fuel_ID : 1
     * Fuel_Name : PETROL
     * Is_Active : 1
     * Make_Name : AUDI
     * Model_ID : 6
     * Model_Name : A4
     * ModifyOn : 1.32985618767E+12
     * Product_Id_New : 1
     * Seating_Capacity : 5
     * Variant_Name : 1.8 MULTITRONIC
     * Vehicle_ID : 9
     * Make_ID :
     * Variant_ID : 9
     * Model_ID1 :
     * Make_Name1 :
     * Model_Name1 :
     * Make_ID1 :
     */

    private String Variant_ID;
    private int V_Id;
    private int CreatedOn;
    private String Cubic_Capacity;
    private String Description;
    private String ExShoroomPrice;
    private String Fuel_ID;
    private String Fuel_Name;
    private int Is_Active;
    private String Make_Name;
    private String Model_ID;
    private String Model_Name;
    private String ModifyOn;
    private String Product_Id_New;
    private String Seating_Capacity;
    private String Variant_Name;
    private String Vehicle_ID;
    private String Make_ID;

    private String Model_ID1;
    private String Make_Name1;
    private String Model_Name1;
    private String Make_ID1;

    protected CarMasterEntity(Parcel in) {
        V_Id = in.readInt();
        CreatedOn = in.readInt();
        Cubic_Capacity = in.readString();
        Description = in.readString();
        ExShoroomPrice = in.readString();
        Fuel_ID = in.readString();
        Fuel_Name = in.readString();
        Is_Active = in.readInt();
        Make_Name = in.readString();
        Model_ID = in.readString();
        Model_Name = in.readString();
        ModifyOn = in.readString();
        Product_Id_New = in.readString();
        Seating_Capacity = in.readString();
        Variant_Name = in.readString();
        Vehicle_ID = in.readString();
        Make_ID = in.readString();
        Variant_ID = in.readString();
        Model_ID1 = in.readString();
        Make_Name1 = in.readString();
        Model_Name1 = in.readString();
        Make_ID1 = in.readString();
    }

    public static final Creator<CarMasterEntity> CREATOR = new Creator<CarMasterEntity>() {
        @Override
        public CarMasterEntity createFromParcel(Parcel in) {
            return new CarMasterEntity(in);
        }

        @Override
        public CarMasterEntity[] newArray(int size) {
            return new CarMasterEntity[size];
        }
    };

    public CarMasterEntity() {
    }

    public int getV_Id() {
        return V_Id;
    }

    public void setV_Id(int V_Id) {
        this.V_Id = V_Id;
    }

    public int getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(int CreatedOn) {
        this.CreatedOn = CreatedOn;
    }

    public String getCubic_Capacity() {
        return Cubic_Capacity;
    }

    public void setCubic_Capacity(String Cubic_Capacity) {
        this.Cubic_Capacity = Cubic_Capacity;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getExShoroomPrice() {
        return ExShoroomPrice;
    }

    public void setExShoroomPrice(String ExShoroomPrice) {
        this.ExShoroomPrice = ExShoroomPrice;
    }

    public String getFuel_ID() {
        return Fuel_ID;
    }

    public void setFuel_ID(String Fuel_ID) {
        this.Fuel_ID = Fuel_ID;
    }

    public String getFuel_Name() {
        return Fuel_Name;
    }

    public void setFuel_Name(String Fuel_Name) {
        this.Fuel_Name = Fuel_Name;
    }

    public int getIs_Active() {
        return Is_Active;
    }

    public void setIs_Active(int Is_Active) {
        this.Is_Active = Is_Active;
    }

    public String getMake_Name() {
        return Make_Name;
    }

    public void setMake_Name(String Make_Name) {
        this.Make_Name = Make_Name;
    }

    public String getModel_ID() {
        return Model_ID;
    }

    public void setModel_ID(String Model_ID) {
        this.Model_ID = Model_ID;
    }

    public String getModel_Name() {
        return Model_Name;
    }

    public void setModel_Name(String Model_Name) {
        this.Model_Name = Model_Name;
    }

    public String getModifyOn() {
        return ModifyOn;
    }

    public void setModifyOn(String ModifyOn) {
        this.ModifyOn = ModifyOn;
    }

    public String getProduct_Id_New() {
        return Product_Id_New;
    }

    public void setProduct_Id_New(String Product_Id_New) {
        this.Product_Id_New = Product_Id_New;
    }

    public String getSeating_Capacity() {
        return Seating_Capacity;
    }

    public void setSeating_Capacity(String Seating_Capacity) {
        this.Seating_Capacity = Seating_Capacity;
    }

    public String getVariant_Name() {
        return Variant_Name;
    }

    public void setVariant_Name(String Variant_Name) {
        this.Variant_Name = Variant_Name;
    }

    public String getVehicle_ID() {
        return Vehicle_ID;
    }

    public void setVehicle_ID(String Vehicle_ID) {
        this.Vehicle_ID = Vehicle_ID;
    }

    public String getMake_ID() {
        return Make_ID;
    }

    public void setMake_ID(String Make_ID) {
        this.Make_ID = Make_ID;
    }

    public String getVariant_ID() {
        return Variant_ID;
    }

    public void setVariant_ID(String Variant_ID) {
        this.Variant_ID = Variant_ID;
    }

    public String getModel_ID1() {
        return Model_ID1;
    }

    public void setModel_ID1(String Model_ID1) {
        this.Model_ID1 = Model_ID1;
    }

    public String getMake_Name1() {
        return Make_Name1;
    }

    public void setMake_Name1(String Make_Name1) {
        this.Make_Name1 = Make_Name1;
    }

    public String getModel_Name1() {
        return Model_Name1;
    }

    public void setModel_Name1(String Model_Name1) {
        this.Model_Name1 = Model_Name1;
    }

    public String getMake_ID1() {
        return Make_ID1;
    }

    public void setMake_ID1(String Make_ID1) {
        this.Make_ID1 = Make_ID1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(V_Id);
        dest.writeInt(CreatedOn);
        dest.writeString(Cubic_Capacity);
        dest.writeString(Description);
        dest.writeString(ExShoroomPrice);
        dest.writeString(Fuel_ID);
        dest.writeString(Fuel_Name);
        dest.writeInt(Is_Active);
        dest.writeString(Make_Name);
        dest.writeString(Model_ID);
        dest.writeString(Model_Name);
        dest.writeString(ModifyOn);
        dest.writeString(Product_Id_New);
        dest.writeString(Seating_Capacity);
        dest.writeString(Variant_Name);
        dest.writeString(Vehicle_ID);
        dest.writeString(Make_ID);
        dest.writeString(Variant_ID);
        dest.writeString(Model_ID1);
        dest.writeString(Make_Name1);
        dest.writeString(Model_Name1);
        dest.writeString(Make_ID1);
    }
}