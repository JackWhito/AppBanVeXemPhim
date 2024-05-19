package com.example.appbanvexemphim.Model;

import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    private int id;
    private int user_id;
    private String create_date;
    private float price;
    private int state;
    private int payment;
    private int service_id;

    public Bill() {
    }

    public Bill(int id, int user_id, String create_date, float price, int state, int payment, int service_id) {
        this.id = id;
        this.user_id = user_id;
        this.create_date = create_date;
        this.price = price;
        this.state = state;
        this.payment = payment;
        this.service_id = service_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }
    @Override
    public String toString(){
        return create_date;
    }
}
