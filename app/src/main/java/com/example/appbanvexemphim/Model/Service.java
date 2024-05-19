package com.example.appbanvexemphim.Model;

import java.io.Serializable;

public class Service implements Serializable {
    private int id;
    private String picture;
    private String name;
    private String description;
    private float price;

    public Service() {
    }

    public Service(int id, String picture, String name, String description, float price) {
        this.id = id;
        this.picture = picture;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

