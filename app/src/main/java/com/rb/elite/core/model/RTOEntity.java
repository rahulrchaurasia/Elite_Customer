package com.rb.elite.core.model;



public class RTOEntity  {
    /**
     * rto_location : Mumbai (Central)- Location- Tardeo
     * series_no : MH-01
     */



    /**
     * rto_id : 1
     * rto_location : Mumbai (Central)- Location- Tardeo
     * series_no : MH-01
     * cityname : Mumbai
     * city_id : 1276
     * price : 350
     * TAT : 4 days
     * product : RC Extract
     * prod_id : 73
     * Category : RTO
     * catg_id : 1
     */

    private int srno;
    private int rto_id;
    private String rto_location;
    private String series_no;
    private String cityname;
    private int city_id;
    private String price;
    private String TAT;
    private String product;
    private  String product_logo;
    private int prod_id;
    private String Category;
    private int catg_id;


    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }
    public int getRto_id() {
        return rto_id;
    }

    public void setRto_id(int rto_id) {
        this.rto_id = rto_id;
    }

    public String getRto_location() {
        return rto_location;
    }

    public void setRto_location(String rto_location) {
        this.rto_location = rto_location;
    }

    public String getSeries_no() {
        return series_no;
    }

    public void setSeries_no(String series_no) {
        this.series_no = series_no;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTAT() {
        return TAT;
    }

    public void setTAT(String TAT) {
        this.TAT = TAT;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public int getCatg_id() {
        return catg_id;
    }

    public void setCatg_id(int catg_id) {
        this.catg_id = catg_id;
    }


    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }
}