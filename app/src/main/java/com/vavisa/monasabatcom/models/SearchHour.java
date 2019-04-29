package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchHour {

    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("Hour")
    @Expose
    private String hour;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
