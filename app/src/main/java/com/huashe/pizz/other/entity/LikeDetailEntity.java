package com.huashe.pizz.other.entity;

/**
 * Created by Jamil
 * Time  2019/1/15
 * Description
 */
public class LikeDetailEntity {

    private String chanpin;
    private  String description;
    private  String image;

    public LikeDetailEntity(String chanpin, String description) {
        this.chanpin = chanpin;
        this.description = description;
    }

    public String getChanpin() {
        return chanpin;
    }

    public void setChanpin(String chanpin) {
        this.chanpin = chanpin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LikeDetailEntity(String chanpin, String description, String image) {
        this.chanpin = chanpin;
        this.description = description;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
