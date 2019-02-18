package com.vavisa.monasabatcom.models.makeAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtrasOrder{

    String _xNameAR;
    String _xNameEN;

    @SerializedName("ExtraId")
    @Expose
    Integer extraId;
    @SerializedName("Price")
    @Expose
    Float price;
    public ExtrasOrder() {
    }

    public ExtrasOrder(Integer extraId, Float price) {
        this.extraId = extraId;
        this.price = price;
    }

    public Integer getExtraId() {
        return extraId;
    }

    public void setExtraId(Integer extraId) {
        this.extraId = extraId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String get_xNameAR() {
        return _xNameAR;
    }

    public void set_xNameAR(String _xNameAR) {
        this._xNameAR = _xNameAR;
    }

    public String get_xNameEN() {
        return _xNameEN;
    }

    public void set_xNameEN(String _xNameEN) {
        this._xNameEN = _xNameEN;
    }
}
