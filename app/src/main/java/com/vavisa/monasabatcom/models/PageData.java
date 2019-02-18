package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageData {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("TitleAR")
    @Expose
    private String titleAR;
    @SerializedName("TitleEN")
    @Expose
    private String titleEN;
    @SerializedName("ContentAR")
    @Expose
    private String contentAR;
    @SerializedName("ContentEN")
    @Expose
    private String contentEN;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleAR() {
        return titleAR;
    }

    public void setTitleAR(String titleAR) {
        this.titleAR = titleAR;
    }

    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getContentAR() {
        return contentAR;
    }

    public void setContentAR(String contentAR) {
        this.contentAR = contentAR;
    }

    public String getContentEN() {
        return contentEN;
    }

    public void setContentEN(String contentEN) {
        this.contentEN = contentEN;
    }
}
