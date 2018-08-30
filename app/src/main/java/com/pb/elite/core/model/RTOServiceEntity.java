package com.pb.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RTOServiceEntity implements Parcelable {

    private int id;
    private String name;

    private String catg_id;
    private String product_logo;
    private int parent_id;
    private List<subcategoryEntity> subcategory;


    protected RTOServiceEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        catg_id = in.readString();
        product_logo = in.readString();
        parent_id = in.readInt();
        subcategory = in.createTypedArrayList(subcategoryEntity.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(catg_id);
        dest.writeString(product_logo);
        dest.writeInt(parent_id);
        dest.writeTypedList(subcategory);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RTOServiceEntity> CREATOR = new Creator<RTOServiceEntity>() {
        @Override
        public RTOServiceEntity createFromParcel(Parcel in) {
            return new RTOServiceEntity(in);
        }

        @Override
        public RTOServiceEntity[] newArray(int size) {
            return new RTOServiceEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatg_id() {
        return catg_id;
    }

    public void setCatg_id(String catg_id) {
        this.catg_id = catg_id;
    }

    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public List<subcategoryEntity> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(List<subcategoryEntity> subcategory) {
        this.subcategory = subcategory;
    }

    /**
     * id : 97
     * name : Vehicle Pick or Drop service available in Mum
     * catg_id : 2
     * product_logo : http://elite.rupeeboss.comdefault.png
     * parent_id : 0
     * subcategory : []
     */




}