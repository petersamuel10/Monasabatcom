package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jesus on 11/24/2018.
 */

public class ServiceExtras {
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("ServiceId")
    @Expose
    private int ServiceId;
    @SerializedName("NameAR")
    @Expose
    private String nameAR;
    @SerializedName("NameEN")
    @Expose
    private String nameEN;
    @SerializedName("Price")
    @Expose
    private String price;

    public ServiceExtras(int id, int serviceId, String nameAR, String nameEN, String price) {
        this.id = id;
        ServiceId = serviceId;
        this.nameAR = nameAR;
        this.nameEN = nameEN;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int serviceId) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
