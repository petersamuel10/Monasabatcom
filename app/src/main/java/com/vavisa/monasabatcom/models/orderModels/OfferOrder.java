package com.vavisa.monasabatcom.models.orderModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferOrder{

    @SerializedName("OfferId")
    @Expose
    Integer offerIdId;
    @SerializedName("Date")
    @Expose
    String date;
    @SerializedName("Time")
    @Expose
    String time;
    @SerializedName("Quantity")
    @Expose
    Integer quantity;
    @SerializedName("Note")
    @Expose
    String note;
    @SerializedName("Price")
    @Expose
    Float price;

    public Integer getOfferIdId() {
        return offerIdId;
    }

    public void setOfferIdId(Integer offerIdId) {
        this.offerIdId = offerIdId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
