package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jesus on 11/24/2018.
 */

public class Services {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("NameAR")
    @Expose
    private String NameAR;
    @SerializedName("NameEN")
    @Expose
    private String NameEN;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("ServiceExtras")
    @Expose
    private ArrayList<ServiceExtras> serviceExtras;

    public Services(int id, String nameAR, String nameEN, String price, ArrayList<ServiceExtras> serviceExtras) {
        this.id = id;
        NameAR = nameAR;
        NameEN = nameEN;
        this.price = price;
        this.serviceExtras = serviceExtras;
    }

    public Services(int id, String nameAR, String nameEN, String price) {
        this.id = id;
        NameAR = nameAR;
        NameEN = nameEN;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<ServiceExtras> getServiceExtras() {
        return serviceExtras;
    }

    public void setServiceExtras(ArrayList<ServiceExtras> serviceExtras) {
        this.serviceExtras = serviceExtras;
    }
}
