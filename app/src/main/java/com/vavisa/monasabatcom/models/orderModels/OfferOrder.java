package com.vavisa.monasabatcom.models.orderModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferOrder{

    //to show in cart activity
    String offer_name_ar;
    String offer_name_en;
    Integer payType;
    Float depositPercentage;

    @SerializedName("OfferId")
    @Expose
    Integer offerIdId;
    @SerializedName("Date")
    @Expose
    String date;
    @SerializedName("Time")
    @Expose
    String time;
    @SerializedName("WomenService")
    @Expose
    Boolean WomenService;
    @SerializedName("Quantity")
    @Expose
    Integer quantity;
    @SerializedName("Note")
    @Expose
    String note;
    @SerializedName("Price")
    @Expose
    Float price;


    public OfferOrder(String offer_name_ar, String offer_name_en, Integer payType,
                      Float depositPercentage, Integer offerIdId, String date, String time,
                      Boolean womenService, Integer quantity, String note, Float price) {
        this.offer_name_ar = offer_name_ar;
        this.offer_name_en = offer_name_en;
        this.payType = payType;
        this.depositPercentage = depositPercentage;
        this.offerIdId = offerIdId;
        this.date = date;
        this.time = time;
        WomenService = womenService;
        this.quantity = quantity;
        this.note = note;
        this.price = price;
    }

    public String getOffer_name_ar() {
        return offer_name_ar;
    }

    public void setOffer_name_ar(String offer_name_ar) {
        this.offer_name_ar = offer_name_ar;
    }

    public String getOffer_name_en() {
        return offer_name_en;
    }

    public void setOffer_name_en(String offer_name_en) {
        this.offer_name_en = offer_name_en;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Float getDepositPercentage() {
        return depositPercentage;
    }

    public void setDepositPercentage(Float depositPercentage) {
        this.depositPercentage = depositPercentage;
    }

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

    public Boolean getWomenService() {
        return WomenService;
    }

    public void setWomenService(Boolean womenService) {
        WomenService = womenService;
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
