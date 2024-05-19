package com.example.appbanvexemphim.Model;

public class Seat {
    private int threaterId;
    private String name;

    public void Seat(int threaterId, String name){
        this.threaterId=threaterId;
        this.name=name;
    }
    public void Seat(){}
    public int getThreaterId() {
        return threaterId;
    }

    public void setThreaterId(int threaterId) {
        this.threaterId = threaterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
