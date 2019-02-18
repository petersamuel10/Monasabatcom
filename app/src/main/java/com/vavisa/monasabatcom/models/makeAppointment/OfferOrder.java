package com.vavisa.monasabatcom.models.makeAppointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vavisa.monasabatcom.models.companyDetails.OfferServices;

import java.util.ArrayList;

public class OfferOrder{

    String _fNameAR;
    String _fNameEN;
    ArrayList<OfferServices> offerServices;

    @SerializedName("OfferId")
    @Expose
    Integer offerIdId;
    @SerializedName("Quantity")
    @Expose
    Integer Quantity;
    @SerializedName("Note")
    @Expose
    String Note;
    @SerializedName("Price")
    @Expose
    Float price;

    public OfferOrder() {
    }

    public OfferOrder(Integer offerIdId, Integer quantity, String note, Float price) {
        this.offerIdId = offerIdId;
        Quantity = quantity;
        Note = note;
        this.price = price;
    }

    public Integer getOfferIdId() {
        return offerIdId;
    }

    public void setOfferIdId(Integer offerIdId) {
        this.offerIdId = offerIdId;
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

    public String get_fNameAR() {
        return _fNameAR;
    }

    public void set_fNameAR(String _fNameAR) {
        this._fNameAR = _fNameAR;
    }

    public String get_fNameEN() {
        return _fNameEN;
    }

    public void set_fNameEN(String _fNameEN) {
        this._fNameEN = _fNameEN;
    }

    public ArrayList<OfferServices> getOfferServices() {
        return offerServices;
    }

    public void setOfferServices(ArrayList<OfferServices> offerServices) {
        this.offerServices = offerServices;
    }
}
