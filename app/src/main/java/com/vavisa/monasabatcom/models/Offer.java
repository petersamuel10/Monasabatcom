package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {


    @SerializedName("Id")
    @Expose
    private int Id;
    @SerializedName("NameAR")
    @Expose
    private String NameAR;
    @SerializedName("NameEN")
    @Expose
    private String NameEN;
    @SerializedName("DescriptionAR")
    @Expose
    private String descriptionAR;
    @SerializedName("DescriptionEN")
    @Expose
    private String descriptionEN;
    @SerializedName("Logo")
    @Expose
    private String Photo;

    public Offer(int id, String nameAR, String nameEN, String descriptionAR, String descriptionEN, String photo) {
        Id = id;
        NameAR = nameAR;
        NameEN = nameEN;
        this.descriptionAR = descriptionAR;
        this.descriptionEN = descriptionEN;
        Photo = photo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNameAR() {
        return NameAR;
    }

    public void setNameAR(String nameAR) {
        NameAR = nameAR;
    }

    public String getNameEN() {
        return NameEN;
    }

    public void setNameEN(String nameEN) {
        NameEN = nameEN;
    }

    public String getDescriptionAR() {
        return descriptionAR;
    }

    public void setDescriptionAR(String descriptionAR) {
        this.descriptionAR = descriptionAR;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
