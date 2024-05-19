package com.example.appbanvexemphim.Model;

import java.io.Serializable;

public class Review implements Serializable {
    private int id;
    private int user_id;
    private int movie_id;
    private String title;
    private String content;
    private String picture;

    public Review(int id, int user_id, int movie_id, String title, String content, String picture) {
        this.id = id;
        this.user_id = user_id;
        this.movie_id = movie_id;
        this.title = title;
        this.content = content;
        this.picture = picture;
    }

    public Review() {
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

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    @Override
    public String toString(){
        return title;
    }
}
