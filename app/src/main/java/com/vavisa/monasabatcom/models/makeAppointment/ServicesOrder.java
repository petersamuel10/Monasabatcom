package com.vavisa.monasabatcom.models.makeAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServicesOrder {

    String _sNameAR;
    String _sNameEN;

    @SerializedName("ServiceId")
    @Expose
    Integer ServiceId;
    @SerializedName("Quantity")
    @Expose
    Integer Quantity;
    @SerializedName("Note")
    @Expose
    String Note;
    @SerializedName("Price")
    @Expose
    Float price;
    @SerializedName("Extras")
    @Expose
    ArrayList<ExtrasOrder> extrasOrder ;

    public ServicesOrder() {
    }

    public ServicesOrder(Integer serviceId, Integer quantity, String note, Float price, ArrayList<ExtrasOrder> extrasOrder) {
        ServiceId = serviceId;
        Quantity = quantity;
        Note = note;
        this.price = price;
        this.extrasOrder = extrasOrder;
    }

    public ServicesOrder(Integer serviceId, Integer quantity, String note, Float price) {
        ServiceId = serviceId;
        Quantity = quantity;
        Note = note;
        this.price = price;
    }

    public Integer getServiceId() {
        return ServiceId;
    }

    public void setServiceId(Integer serviceId) {
        ServiceId = serviceId;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
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

    public String get_sNameAR() {
        return _sNameAR;
    }

    public void set_sNameAR(String _sNameAR) {
        this._sNameAR = _sNameAR;
    }

    public String get_sNameEN() {
        return _sNameEN;
    }

    public void set_sNameEN(String _sNameEN) {
        this._sNameEN = _sNameEN;
    }
}
