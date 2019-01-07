package com.rb.elite.core.model;

public class NonRtoSpeciallistEntity {
    /**
     * productid : 137
     * spName : 1 Year Subscription
     * spPrice : 1099
     */

    private int productid;
    private String spName;
    private String spPrice;


    private boolean isCheck;

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpPrice() {
        return spPrice;
    }

    public void setSpPrice(String spPrice) {
        this.spPrice = spPrice;
    }

    public boolean getCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}