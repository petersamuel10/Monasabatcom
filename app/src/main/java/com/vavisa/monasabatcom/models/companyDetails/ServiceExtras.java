package com.vavisa.monasabatcom.models.companyDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jesus on 11/24/2018.
 */

public class ServiceExtras {

    //for show in appointment details
    private boolean isSelected = false;

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ServiceId")
    @Expose
    private Integer ServiceId;
    @SerializedName("NameAR")
    @Expose
    private String nameAR;
    @SerializedName("NameEN")
    @Expose
    private String nameEN;
    @SerializedName("Price")
    @Expose
    private Float price;

    public ServiceExtras() {
    }

    public ServiceExtras(Integer id, Integer serviceId, String nameAR, String nameEN, Float price) {
        this.id = id;
        ServiceId = serviceId;
        this.nameAR = nameAR;
        this.nameEN = nameEN;
        this.price = price;
    }

    //for order Details
    public ServiceExtras(Integer id, String nameAR, String nameEN, Float price) {
        this.id = id;
        this.nameAR = nameAR;
        this.nameEN = nameEN;
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceId() {
        return ServiceId;
    }

    public void setServiceId(Integer serviceId) {
        ServiceId = serviceId;
    }

    public String getNameAR() {
        return nameAR;
    }

    public void setNameAR(String nameAR) {
        this.nameAR = nameAR;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
