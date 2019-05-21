package com.vavisa.monasabatcom.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferDetails {

    @SerializedName("Id")
    @Expose
    Integer id;
    @SerializedName("NameAR")
    @Expose
    String nameAR;
    @SerializedName("NameEN")
    @Expose
    String nameEN;
    @SerializedName("Date")
    @Expose
    String date;
    @SerializedName("Time")
    @Expose
    String time;
    @SerializedName("Price")
    @Expose
    Float price;
    @SerializedName("Qty")
    @Expose
    Integer qty;
    @SerializedName("Total")
    @Expose
    Float total;
    @SerializedName("Note")
    @Expose
    String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
