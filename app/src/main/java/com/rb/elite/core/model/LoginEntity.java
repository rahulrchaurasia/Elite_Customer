package com.rb.elite.core.model;

import java.util.List;

public class LoginEntity {
    private List<UserEntity> userdetails;
    private List<OrderStatusEntity> orderstatuslist;

    public List<UserEntity> getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(List<UserEntity> userdetails) {
        this.userdetails = userdetails;
    }

    public List<OrderStatusEntity> getOrderstatuslist() {
        return orderstatuslist;
    }

    public void setOrderstatuslist(List<OrderStatusEntity> orderstatuslist) {
        this.orderstatuslist = orderstatuslist;
    }


}