package com.pb.elite.core.model;

public  class UserConstatntEntity {


    /**
     * make : SKODA
     * model : RAPID
     * companylogo : http://elite.rupeeboss.com/images/upload/168reli.png
     * companyname : Reliance General Insurance
     */

    private String make;
    private String model;
    private String companylogo;
    private String companyname;
    private String vehicleno;
    private String homepopup;

    /**
     * vehicleno : KA04ML6747
     * homepopup : http://elite.rupeeboss.com/images/upload/168reli.png
     */



    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCompanylogo() {
        return companylogo;
    }

    public void setCompanylogo(String companylogo) {
        this.companylogo = companylogo;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getHomepopup() {
        return homepopup;
    }

    public void setHomepopup(String homepopup) {
        this.homepopup = homepopup;
    }
}