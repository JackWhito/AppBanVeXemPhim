package com.example.appbanvexemphim.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ticket implements Serializable {
    private int id;
    private int show_id;
    private String seatName;
    private float price;
    private int state;

    public Ticket() {
    }

    public Ticket(int id, int show_id, String seatName, float price, int state) {
        this.id = id;
        this.show_id = show_id;
        this.seatName = seatName;
        this.price = price;
        this.state = state;
    }

    protected Ticket(Parcel in) {
        id = in.readInt();
        show_id = in.readInt();
        seatName = in.readString();
        price = in.readFloat();
        state = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShow_id() {
        return show_id;
    }

    public void setShow_id(int show_id) {
        this.show_id = show_id;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
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

}
