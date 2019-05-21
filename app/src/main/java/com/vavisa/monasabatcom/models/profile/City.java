package com.vavisa.monasabatcom.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("Id")
    @Expose
    private int Id;
    @SerializedName("GovernorateId")
    @Expose
    private int GovernorateId;
    @SerializedName("GovernorateNameAR")
    @Expose
    private String GovernorateNameAR;
    @SerializedName("GovernorateNameEN")
    @Expose
    private String GovernorateNameEN;
    @SerializedName("NameAR")
    @Expose
    private String NameAR;
    @SerializedName("NameEN")
    @Expose
    private String NameEN;

    public City(int id, int governorateId, String governorateNameAR, String governorateNameEN, String nameAR, String nameEN) {
        Id = id;
        GovernorateId = governorateId;
        GovernorateNameAR = governorateNameAR;
        GovernorateNameEN = governorateNameEN;
        NameAR = nameAR;
        NameEN = nameEN;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getGovernorateId() {
        return GovernorateId;
    }

    public void setGovernorateId(int governorateId) {
        GovernorateId = governorateId;
    }

    public String getGovernorateNameAR() {
        return GovernorateNameAR;
    }

    public void setGovernorateNameAR(String governorateNameAR) {
        GovernorateNameAR = governorateNameAR;
    }

    public String getGovernorateNameEN() {
        return GovernorateNameEN;
    }

    public void setGovernorateNameEN(String governorateNameEN) {
        GovernorateNameEN = governorateNameEN;
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
}