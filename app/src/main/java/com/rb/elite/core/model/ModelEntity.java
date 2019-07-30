package com.rb.elite.core.model;

import java.util.List;

public class ModelEntity {
    /**
     * Model : BUS
     * ModelID : 15
     * Variant : [{"Variant":"ALPSV 4/88","VariantID":12},{"Variant":"VIKING ALPSV 4/186","VariantID":13},{"Variant":"VIKING ALPSV 4/185","VariantID":3602}]
     */

    private String Model;
    private int ModelID;
    private List<VariantEntity> Variant;

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public int getModelID() {
        return ModelID;
    }

    public void setModelID(int ModelID) {
        this.ModelID = ModelID;
    }

    public List<VariantEntity> getVariant() {
        return Variant;
    }

    public void setVariant(List<VariantEntity> Variant) {
        this.Variant = Variant;
    }

}