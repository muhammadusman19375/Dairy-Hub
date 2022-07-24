package com.example.fyp.models;

public class UsersModel {
    private String uid,cowname,cowbreed,parturition,image_cow,date,dayleft,insemType;

    public UsersModel() {
    }

    public UsersModel(String uid, String cowname, String cowbreed, String parturition, String image_cow, String date, String dayleft, String insemType) {
        this.uid = uid;
        this.cowname = cowname;
        this.cowbreed = cowbreed;
        this.parturition = parturition;
        this.image_cow = image_cow;
        this.date = date;
        this.dayleft = dayleft;
        this.insemType = insemType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCowname() {
        return cowname;
    }

    public void setCowname(String cowname) {
        this.cowname = cowname;
    }

    public String getCowbreed() {
        return cowbreed;
    }

    public void setCowbreed(String cowbreed) {
        this.cowbreed = cowbreed;
    }

    public String getParturition() {
        return parturition;
    }

    public void setParturition(String parturition) {
        this.parturition = parturition;
    }

    public String getImage_cow() {
        return image_cow;
    }

    public void setImage_cow(String image_cow) {
        this.image_cow = image_cow;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayleft() {
        return dayleft;
    }

    public void setDayleft(String dayleft) {
        this.dayleft = dayleft;
    }

    public String getInsemType() {
        return insemType;
    }

    public void setInsemType(String insemType) {
        this.insemType = insemType;
    }
}
