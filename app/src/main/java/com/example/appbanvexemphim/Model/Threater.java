package com.example.appbanvexemphim.Model;

public class Threater {
    private int id;
    private String name;
    private int cinema_id;

    public Threater() {
    }

    public Threater(int id, String name, int cinema_id) {
        this.id = id;
        this.name = name;
        this.cinema_id = cinema_id;
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

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }
}
