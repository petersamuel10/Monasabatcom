package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("Id")
    @Expose
    private Integer Id;
    @SerializedName("NameAR")
    @Expose
    private String NameAR;
    @SerializedName("NameEN")
    @Expose
    private String NameEN;
    @SerializedName("AboutAR")
    @Expose
    private String AboutAR;
    @SerializedName("AboutEN")
    @Expose
    private String AboutEN;
    @SerializedName("Rating")
    @Expose
    private Float Rating;
    @SerializedName("RatingCount")
    @Expose
    private Integer RatingCount;
    @SerializedName("Logo")
    @Expose
    private String Logo;


    public Company(Integer id, String nameAR, String nameEN, String aboutAR, String aboutEN, Float rating, Integer ratingCount, String logo) {
        Id = id;
        NameAR = nameAR;
        NameEN = nameEN;
        AboutAR = aboutAR;
        AboutEN = aboutEN;
        Rating = rating;
        RatingCount = ratingCount;
        Logo = logo;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
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

    public String getAboutAR() {
        return AboutAR;
    }

    public void setAboutAR(String aboutAR) {
        AboutAR = aboutAR;
    }

    public String getAboutEN() {
        return AboutEN;
    }

    public void setAboutEN(String aboutEN) {
        AboutEN = aboutEN;
    }

    public Float getRating() {
        return Rating;
    }

    public void setRating(Float rating) {
        Rating = rating;
    }

    public Integer getRatingCount() {
        return RatingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        RatingCount = ratingCount;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }
}
