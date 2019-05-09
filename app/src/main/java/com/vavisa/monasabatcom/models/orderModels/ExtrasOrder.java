package com.vavisa.monasabatcom.models.orderModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtrasOrder {

    //to show in cart activity
    String service_name_ar;
    String service_name_en;

    @SerializedName("ExtraId")
    @Expose
    Integer extraId;
    @SerializedName("Quantity")
    @Expose
    Integer quantity;
    @SerializedName("Price")
    @Expose
    Float price;

    public ExtrasOrder(String service_name_ar, String service_name_en, Integer extraId, Integer quantity, Float price) {
        this.service_name_ar = service_name_ar;
        this.service_name_en = service_name_en;
        this.extraId = extraId;
        this.quantity = quantity;
        this.price = price;
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

    public Integer getExtraId() {
        return extraId;
    }

    public void setExtraId(Integer extraId) {
        this.extraId = extraId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
