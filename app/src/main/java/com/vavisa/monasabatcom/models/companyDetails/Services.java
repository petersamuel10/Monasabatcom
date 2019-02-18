package com.vavisa.monasabatcom.models.companyDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jesus on 11/24/2018.
 */

public class Services {

    //for show in appointment details
     boolean isSelected = false;

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("NameAR")
    @Expose
    private String NameAR;
    @SerializedName("NameEN")
    @Expose
    private String NameEN;
    @SerializedName("Price")
    @Expose
    private Float price;
    @SerializedName("ServiceExtras")
    @Expose
    private ArrayList<ServiceExtras> serviceExtras;
    @SerializedName("Qty")
    @Expose
    private Integer qty;
    @SerializedName("Note")
    @Expose
    private String note;

    public Services() {
    }

    public Services(Integer id, String nameAR, String nameEN, Float price, ArrayList<ServiceExtras> serviceExtras) {
        this.id = id;
        NameAR = nameAR;
        NameEN = nameEN;
        this.price = price;
        this.serviceExtras = serviceExtras;
    }

    public Services(Integer id, String nameAR, String nameEN, Float price) {
        this.id = id;
        NameAR = nameAR;
        NameEN = nameEN;
        this.price = price;
    }

    //for order details
    public Services(Integer id, String nameAR, String nameEN, Float price, Integer qty, String note, ArrayList<ServiceExtras> serviceExtras) {
        this.id = id;
        NameAR = nameAR;
        NameEN = nameEN;
        this.price = price;
        this.serviceExtras = serviceExtras;
        this.qty = qty;
        this.note = note;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public ArrayList<ServiceExtras> getServiceExtras() {
        return serviceExtras;
    }

    public void setServiceExtras(ArrayList<ServiceExtras> serviceExtras) {
        this.serviceExtras = serviceExtras;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
