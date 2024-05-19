package com.example.appbanvexemphim.Model;


import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String name;
    private int duration;

    private String requireAge;
    private int type_id;
    private String desciption;
    private float rating;
    private String image;

    public Movie() {
    }

    public Movie(int id, String name, int duration, String requireAge, int type_id, String description, float rating) {
        this.id = id;
        this.name = name;
        this.duration = duration;

        this.requireAge = requireAge;
        this.type_id = type_id;
        this.desciption = description;
        this.rating = rating;
    }
    public Movie(String name, String imageResource, String description ){
        this.name = name;
        this.image = imageResource;
        this.desciption=description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }



    // Getter and Setter for 'type_id'
    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    // Getter and Setter for 'description'
    public String getDescription() {
        return desciption;
    }

    public void setDescription(String description) {
        this.desciption = description;
    }
    // Getter and Setter for 'duration'
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Getter and Setter for 'requireAge'
    public String getRequireAge() {
        return requireAge;
    }

    public void setRequireAge(String requireAge) {
        this.requireAge = requireAge;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString(){
        return name;
    }

}
