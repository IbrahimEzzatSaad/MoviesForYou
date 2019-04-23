package com.example.nightblue.moviesforyou.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Image implements Serializable{
    private String name;
    private String image;
    private String timestamp;
    private String URL;
    private ArrayList<String> genre;

    public Image() {
    }

    public Image(String name, String image, String timestamp,ArrayList<String> genre,String URL) {
        this.name = name;
        this.image = image;
        this.timestamp = timestamp;
        this.genre = genre;
        this.URL = URL;
    }


    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

}
