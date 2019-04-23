package com.example.nightblue.moviesforyou.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Image implements Serializable{
    private String name;
    private String small, medium, large;
    private String timestamp;
    private String URL;
    private ArrayList<String> genre;

    public Image() {
    }

    public Image(String name, String small, String medium, String large, String timestamp,ArrayList<String> genre,String URL) {
        this.name = name;
        this.small = small;
        this.medium = medium;
        this.large = large;
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


    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
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
