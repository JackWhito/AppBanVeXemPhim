package com.example.appbanvexemphim.Model;

import java.io.Serializable;
import java.util.Date;

public class Show implements Serializable {
    private int id;
    private int movie_id;
    private int threater_id;
    private String datetime;

    public Show() {
    }

    public Show(int id, int movie_id, int threater_id, String datetime) {
        this.id = id;
        this.movie_id = movie_id;
        this.threater_id = threater_id;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getThreater_id() {
        return threater_id;
    }

    public void setThreater_id(int threater_id) {
        this.threater_id = threater_id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    @Override
    public String toString(){
        return datetime;
    }
}
