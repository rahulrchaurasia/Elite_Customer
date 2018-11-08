package com.pb.elite.core.requestmodel;

/**
 * Created by IN-RB on 03-11-2018.
 */

public class RegisterRequest {


    /**
     * name : Nitin Teat
     * emailid : patil44@gmail.com
     * mobile : 7865503612
     * city : Nashik
     * state : Maharashtra
     * area : vihar
     * pincode : 768732
     * vehicle_no : MH03AS4561
     * policy_no : HGHDD7665
     * password : 1234567
     */

    private String name;
    private String emailid;
    private String mobile;
    private String city;
    private String state;
    private String area;
    private String pincode;
    private String vehicle_no;
    private String policy_no;
    private String password;


    private String otp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getPolicy_no() {
        return policy_no;
    }

    public void setPolicyNo(String policy_no) {
        this.policy_no = policy_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPolicy_no(String policy_no) {
        this.policy_no = policy_no;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
