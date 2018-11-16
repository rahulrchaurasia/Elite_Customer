package com.pb.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IN-RB on 06-08-2018.
 */

public class subcategoryEntity implements Parcelable{


    /**
     * id : 116
     * name :  Addition of Hypothecation in Vehicle Registration Certificate
     * catg_id : 1
     * product_logo : http://elite.rupeeboss.com/images/icons/RTO/Addition-of-Hypothecation-in-Vehicle-Registration-Certificate.png
     * parent_id : 115
     */

    private int id;
    private String name;
    private String catg_id;
    private String product_logo;
    private int parent_id;
    private String productcode;


    protected subcategoryEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        catg_id = in.readString();
        product_logo = in.readString();
        parent_id = in.readInt();
        productcode = in.readString();
    }

    public static final Creator<subcategoryEntity> CREATOR = new Creator<subcategoryEntity>() {
        @Override
        public subcategoryEntity createFromParcel(Parcel in) {
            return new subcategoryEntity(in);
        }

        @Override
        public subcategoryEntity[] newArray(int size) {
            return new subcategoryEntity[size];
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
    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(catg_id);
        dest.writeString(product_logo);
        dest.writeInt(parent_id);
        dest.writeString(productcode);
    }
}
