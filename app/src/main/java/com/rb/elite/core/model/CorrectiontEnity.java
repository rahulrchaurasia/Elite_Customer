package com.rb.elite.core.model;

public class CorrectiontEnity {
    /**
     * doc_id : 3
     * document_name : Form 29 - Declaration of seller
     * document_description :
     */

    private String doc_id;
    private String document_name;

    private boolean isCheck;



    public CorrectiontEnity(String doc_id, String document_name,  boolean isCheck) {
        this.doc_id = doc_id;
        this.document_name = document_name;

        this.isCheck = isCheck;

    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }



    public boolean getCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

}