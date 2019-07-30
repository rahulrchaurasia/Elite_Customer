package com.rb.elite.core.model;

import java.util.List;

public class MakeEntity {
    /**
     * Make : ACCURA
     * MakeID : 1
     * Model : [{"Model":"GENX","ModelID":1,"Variant":[{"Variant":"STD","VariantID":1}]},{"Model":"YADI","ModelID":2,"Variant":[{"Variant":"STD","VariantID":2}]}]
     */

    private String Make;
    private int MakeID;
    private List<ModelEntity> Model;

    public String getMake() {
        return Make;
    }

    public void setMake(String Make) {
        this.Make = Make;
    }

    public int getMakeID() {
        return MakeID;
    }

    public void setMakeID(int MakeID) {
        this.MakeID = MakeID;
    }

    public List<ModelEntity> getModel() {
        return Model;
    }

    public void setModel(List<ModelEntity> Model) {
        this.Model = Model;
    }


}