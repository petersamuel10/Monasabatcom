package com.vavisa.monasabatcom.models.orderModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServicesOrder {

    //to show in cart activity
    String service_name_ar;
    String service_name_en;
    Integer payType;

    @SerializedName("ServiceId")
    @Expose
    Integer serviceId;
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
    @SerializedName("Extras")
    @Expose
    ArrayList<ExtrasOrder> extrasOrder;

    public ServicesOrder(String service_name_ar, String service_name_en, Integer payType, Integer serviceId, String date,
                         String time, Integer quantity, String note, Float price, ArrayList<ExtrasOrder> extrasOrder) {
        this.service_name_ar = service_name_ar;
        this.service_name_en = service_name_en;
        this.payType = payType;
        this.serviceId = serviceId;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
        this.note = note;
        this.price = price;
        this.extrasOrder = extrasOrder;
    }

    public String getService_name_ar() {
        return service_name_ar;
    }

    public void setService_name_ar(String service_name_ar) {
        this.service_name_ar = service_name_ar;
    }

    public String getService_name_en() {
        return service_name_en;
    }

    public void setService_name_en(String service_name_en) {
        this.service_name_en = service_name_en;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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

    public ArrayList<ExtrasOrder> getExtrasOrder() {
        return extrasOrder;
    }

    public void setExtrasOrder(ArrayList<ExtrasOrder> extrasOrder) {
        this.extrasOrder = extrasOrder;
    }
}
