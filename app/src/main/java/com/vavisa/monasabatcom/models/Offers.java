package com.vavisa.monasabatcom.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jesus on 11/25/2018.
 */

public class Offers {

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
    @SerializedName("OfferServices")
    @Expose
    private OfferServices offerServices;

    public Offers(int id, String nameAR, String nameEN, String price, OfferServices offerServices) {
        this.id = id;
        NameAR = nameAR;
        NameEN = nameEN;
        this.price = price;
        this.offerServices = offerServices;
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

    public OfferServices getOfferServices() {
        return offerServices;
    }

    public void setOfferServices(OfferServices offerServices) {
        this.offerServices = offerServices;
    }
}
