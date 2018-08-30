package com.pb.elite.core.model;

public  class OrderDetailEntity {
    /**
     * order_id : 167
     * customer_name : prd00
     * product_name :  Ownership Transfer In Vehicle Registration Certificate
     * amount : 4200.00
     * payment_status : 0
     * status : 1
     * order_status : Pending
     * payment_date : 2018-08-10 15:38:08
     * product_id : 109
     * userdoccount : 0
     * doccount : 9
     * DocumentPending : 1
     */

    private int order_id;
    private String customer_name;
    private String product_name;
    private String amount;
    private String payment_status;
    private String status;
    private String order_status;
    private String payment_date;
    private String product_id;
    private int userdoccount;
    private int doccount;
    private int DocumentPending;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getUserdoccount() {
        return userdoccount;
    }

    public void setUserdoccount(int userdoccount) {
        this.userdoccount = userdoccount;
    }

    public int getDoccount() {
        return doccount;
    }

    public void setDoccount(int doccount) {
        this.doccount = doccount;
    }

    public int getDocumentPending() {
        return DocumentPending;
    }

    public void setDocumentPending(int DocumentPending) {
        this.DocumentPending = DocumentPending;
    }





}