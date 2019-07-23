package com.rb.elite.core.model;

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

    private String contactno;
    private String emailid;

    private String  dbversion;

    private String VersionCode;
    private String IsForceUpdate ;

    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String versionCode) {
        VersionCode = versionCode;
    }

    public String getIsForceUpdate() {
        return IsForceUpdate;
    }

    public void setIsForceUpdate(String isForceUpdate) {
        IsForceUpdate = isForceUpdate;
    }




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

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getDbversion() {
        return dbversion;
    }

    public void setDbversion(String dbversion) {
        this.dbversion = dbversion;
    }
}