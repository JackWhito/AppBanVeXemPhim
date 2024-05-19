package com.example.appbanvexemphim.Model;

import java.io.Serializable;

public class Cinema implements Serializable {
    private int id;
    private String picture;

    private String name;
    private String address;

    public Cinema() {
    }

    public Cinema(int id, String picture, String name, String address) {
        this.id = id;
        this.picture = picture;
        this.name = name;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    @Override
    public String toString(){
        return name;
    }
}
