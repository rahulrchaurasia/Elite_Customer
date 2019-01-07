package com.rb.elite.core.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CategoryEntity extends RealmObject {
    /**
     * id : 1
     * name : RTO
     */
    @PrimaryKey
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}